const S3 = require('aws-sdk/clients/s3');

const CustomError = require('./CustomError');

const s3Config = {
  region: process.env.S3_BUCKET_REGION,
};

if (process.env.NODE_ENV === 'local') {
  s3Config.accessKeyId = process.env.S3_ACCESS_KEY_ID;
  s3Config.secretAccessKey = process.env.S3_SECRET_ACCESS_KEY;
}

const s3 = new S3(s3Config);

exports.createPresignedPost = (
  key,
  bucket = process.env.S3_BUCKET_NAME,
  ttl = process.env.S3_PRESIGNED_POST_TTL
) => {
  return new Promise((resolve, reject) => {
    try {
      s3.createPresignedPost(
        {
          Bucket: bucket,
          Fields: {
            key,
          },
          Expires: parseInt(ttl),
          Conditions: [
            ['content-length-range', 0, 10 * 1000 * 1000], // 10MB 제한
            ['starts-with', '$Content-Type', ''], // Content-Type 필수 입력
          ],
        },
        (err, data) => {
          if (err) reject(err);
          return resolve(data);
        }
      );
    } catch (err) {
      console.error(err);
      throw new CustomError('AWS_S3_FAILED', 500);
    }
  });
};

exports.getSignedUrl = (
  key,
  bucket = process.env.S3_BUCKET_NAME,
  ttl = process.env.S3_PRESIGNED_GET_TTL
) => {
  return new Promise((resolve, reject) => {
    try {
      s3.getSignedUrl(
        'getObject',
        {
          Bucket: bucket,
          Key: key,
          Expires: parseInt(ttl),
        },
        (err, url) => {
          if (err) reject(err);
          return resolve(url);
        }
      );
    } catch (err) {
      console.error(err);
      throw new CustomError('AWS_S3_FAILED', 500);
    }
  });
};

exports.deleteObject = async (key, bucket = process.env.S3_BUCKET_NAME) => {
  try {
    await s3
      .deleteObject({
        Bucket: bucket,
        Key: key,
      })
      .promise();
  } catch (err) {
    console.error(err);
    throw new CustomError('AWS_S3_FAILED', 500);
  }
};

exports.deleteObjects = async (keys, bucket = process.env.S3_BUCKET_NAME) => {
  try {
    if (typeof keys === 'string') keys = [keys];
    if (!keys || keys.length === 0) return;
    s3.deleteObjects(
      {
        Bucket: bucket,
        Delete: { Objects: keys.map(key => ({ Key: key })) },
      },
      (err, data) => {
        if (err) {
          console.error(err);
          throw new CustomError('AWS_S3_FAILED', 500);
        }
      }
    );
  } catch (err) {
    console.error(err);
    throw new CustomError('AWS_S3_FAILED', 500);
  }
};
