const { v4: uuid } = require('uuid');
const { house, food } = require('../../utils/category');

exports.postClipService = async (trx, user, { targetId, job = true }) => {
  const exists = await trx('clip')
    .select()
    .where({ target_id: targetId, user_id: user.uid })
    .first();
  if (exists) {
    await trx('clip').delete().where({ target_id: targetId, user_id: user.uid });
  } else {
    await trx('clip').insert({ id: uuid(), target_id: targetId, user_id: user.uid, job });
  }
};

exports.getClipService = async (trx, user) => {
  const clips = await trx('clip').select('target_id', 'job').where({ user_id: user.uid });
  const jobs = [];
  const houses = [];
  const foods = [];
  const tour_spots = [];

  for (const clip of clips) {
    if (clip.job === 1) {
      jobs.push(clip.target_id);
    } else if (clip.job === 0) {
      if (clip.targetId in house) houses.push(clip.target_id);
      else if (clip.targetId in food) foods.push(clip.target_id);
      else tour_spots.push(clip.target_id);
    }
  }
  const h = await trx('house').select('id', 'name', 'photo', 'addr').whereIn('id', houses);
  const f = await trx('food').select('id', 'name', 'photo', 'addr', 'info').whereIn('id', foods);
  const t = await trx('tour_spot')
    .select('id', 'name', 'photo', 'addr', 'info')
    .whereIn('id', tour_spots);
  const job = await trx('job')
    .select('id', 'name', 'photo', 'location', 'start_date', 'end_date', 'payment ')
    .whereIn('id', jobs);
  const play = await [...h, ...f, ...t];
  return { job, play };
};

exports.getBillService = async trx => {
  const bills = await trx('bill').select('start_date', 'end_date', 'id', 'location');
  const result = [];
  for (const bill of bills) {
    const exist = await trx('bill_review').where({ bill_id: bill.id }).first();
    const h = await trx
      .from({ hb: 'house_bill' })
      .select('h.id', 'h.name', 'h.photo', 'h.addr')
      .leftJoin({ h: 'house' }, 'hb.house_id', 'h.id')
      .where('hb.bill_id', bill.id);
    const f = await trx
      .from({ fb: 'food_bill' })
      .select('f.id', 'f.name', 'f.photo', 'f.addr', 'f.info')
      .leftJoin({ f: 'food' }, 'f.id', 'fb.food_id')
      .where('fb.bill_id', bill.id);
    const t = await trx
      .from({ tsb: 'tour_spot_bill' })
      .select('ts.id', 'ts.name', 'ts.photo', 'ts.addr', 'ts.info')
      .leftJoin({ ts: 'tour_spot' }, 'ts.id', 'tsb.tour_spot_id')
      .where('tsb.bill_id', bill.id);
    const j = await trx
      .from({ jb: 'job_bill' })
      .select('j.id', 'j.name', 'j.photo', 'j.location', 'j.start_date', 'j.end_date', 'j.payment ')
      .leftJoin({ j: 'job' }, 'j.id', 'jb.job_id')
      .where('bill_id', bill.id);
    bill.exists = exist ? 1 : 0;
    result.push({
      ...bill,
      list: { houses: h ?? null, foods: f ?? null, tourSpots: t ?? null, jobs: j ?? null },
    });
  }
  return result;
};
