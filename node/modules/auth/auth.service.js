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
  // const play = [];

  // play.push(...(await trx('house').select('id', 'name', 'photo', 'addr').whereIn('id', houses)));
  // play.push(
  //   ...(await trx('food').select('id', 'name', 'photo', 'addr', 'info').whereIn('id', foods))
  // );
  // play.push(
  //   ...(await trx('tour_spot')
  //     .select('id', 'name', 'photo', 'addr', 'info')
  //     .whereIn('id', tour_spots))
  // );
  // const job = await trx('job')
  //   .select('id', 'name', 'photo', 'location', 'start_date', 'end_date', 'payment ')
  //   .whereIn('id', jobs);
  return { job, play, clips, houses, foods, tour_spots, t };
};
