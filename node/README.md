# Express Boilerplate

본 템플릿 저장소는 [Express 프레임워크](https://expressjs.com/)를 기반으로 작성되었습니다.

## How to start

로컬 환경 기준으로 작성되었습니다. 당사 서버 설정 가이드를 따르거나, 필요한 구성 요소들을 설치한 뒤 설정합니다.

우선 IDE나 텍스트 편집기 등을 사용해 `PROJECT_NAME` 텍스트를 모두 찾아 프로젝트명으로 바꿉니다(예: `Edit > Replace in Files` 또는 `Ctrl + Shift + H` 명령어 등).

### `.env`

로컬 환경, 또는 필요에 따라 클라우드 환경 변수에 값을 채워넣습니다.

```sh
# 프로젝트 설정
API_VERSION=v1  # API 버전
PORT=8080   # 포트 지정
JWT_SECRET=jwt_secret # JWT Signature 키

# RDS 설정
DB_HOST=localhost
DB_PORT=3306
DB_USER=admin
DB_PASS=db_password
DB_NAME=database_name
DB_POOL_MIN=min_connection_pool
DB_POOL_MAX=max_connection_pool

# Redis 설정
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_DB_INDEX=0
REDIS_PASS=redis_password
REDIS_ACCESS_TOKEN_TTL=access_token_ttl
REDIS_REFRESH_TOKEN_TTL=refresh_token_ttl

# AWS S3 설정
S3_ACCESS_KEY_ID=aws_s3_access_key_id # 로컬 환경
S3_SECRET_ACCESS_KEY=aws_s3_secret_access_key # 로컬 환경
S3_BUCKET_REGION=aws_s3_bucket_region
S3_BUCKET_NAME=aws_s3_bucket_name
S3_PRESIGNED_GET_TTL=ttl_presigned_url_for_get
S3_PRESIGNED_POST_TTL=ttl_presigned_url_for_post
```

### Commands

```sh
npm install
npm start
```

## Architecture

### Directories

```sh
project
├─📂config  # 환경변수 및 express 설정
│  ├─express.js
│  ├─morgan.js
│  └─winston.js
├─📂database  # 데이터베이스 설정
│  ├─knex.js
│  └─redis.js
├─📂middlewares # 미들웨어
│  ├─asyncWrapper.js
│  ├─authenticator.js
│  └─index.js
├─📂modules # 기능 모듈
│  ├─📂common
│  │  ├─common.controller.js  # 컨트롤러 레이어
│  │  ├─common.router.js  # 라우터
│  │  ├─common.service.js # 서비스 레이어(비즈니스 로직)
│  │  └─common.validator.js # 입력 유효성 검증
│  └─index.js
├─📂utils # 유틸 모듈
├─.env  # 프로젝트 환경변수
├─pm2.json  # pm2 설정
└─server.js # 서버 엔트리 포인트
```

### Configuration

#### Winston

- 특정 라우터에서 로그를 따로 남길 수 있도록 Winston 라이브러리를 사용합니다.
- 사용하고자 하는 라우터에서 아래와 같이 설정합니다.

```js
const express = require('express');
const app = express();
const winston = require('./winston.js');

/* Lots of middlewares and configurations */

app.post('/users', async (req, res, next) => {
  try {
    /* Business logic here... */
    winston.info({ url: req.originalUrl, data: req.body });
  } catch (err) {
    /* Error handling */
  }
});
```

#### 기능 모듈 생성 스크립트

- 새로운 모듈을 만들 때마다 파일 만들기가 귀찮아서 아주 간단한 쉘 스크립트를 작성해보았습니다.
- 스크립트 사용을 위해서 아래와 같이 설정합니다.
  1. _MacOS 기준으로_ 터미널에서 `cd` 명령어를 입력해 홈 디렉토리로 이동합니다.
  2. `vi ~/.zshrc` 명령어를 입력하면 zsh 관련 스크립트 파일을 수정할 수 있습니다.
  - 본 프로젝트의 `.init_module.sh` 파일에 있는 내용을 그대로 `.zshrc` 파일에 붙여넣습니다.
  - 또는 `.zshrc` 파일 어딘가에 `source 프로젝트경로/.init_module.sh`를 입력하여 스크립트 파일을 실행할 수 있도록 합니다.
  3. 저장하고 `source ~/.zshrc` 명령어를 통해 수정한 스크립트를 활성화합니다.
- `modules/` 디렉터리 내 원하는 경로로 이동해 `init_module 모듈명` 명령어를 입력해 모듈을 생성할 수 있습니다.
- **root route에 해당 모듈의 router를 연결하는 작업은 직접 하셔야 합니다(아니면 쉘 스크립트를 업그레이드 해주세요!).**

### Database

#### Knex

[Knex](https://knexjs.org/)를 사용해 데이터베이스에 접근합니다. `knex` 모듈은 그 자체로 configuration 객체를 받는 함수입니다. 전달받은 configuration은 캐싱되며 모든 연결에 재사용됩니다.

##### Connection

Connection pool은 클라이언트 내부적으로 [tarn.js](https://github.com/vincit/tarn.js) 라이브러리를 사용해 만들어집니다. MySQL 기본값은 `min: 2, max: 10`으로 설정되어 있습니다. Connection pool을 제거하려면 `knex.destroy([callback])`을 사용하세요. 콜백 또는 프로미스 체이닝 중 한 가지를 사용하시기 바랍니다.

##### Options

`asyncStackTraces`가 `true`일 경우 query builder, raw query, schema builder를 사용할 때 호출 순서를 기억합니다. DB 드라이버에서 에러가 발생할 경우 호출 순서(스택)를 새로 생성하는 대신 기존 스택을 사용합니다. 이는 node.js/V8가 스택을 날려버리는 await 특성을 막기 때문에 운영 시에는 기본 옵션 상태인 `false`로 하는 것을 권장합니다.

#### Redis

Redis 모듈은 내장 함수인 `util.promisify`를 사용해 비동기 함수화를 해줍니다.

### Middlewares

#### asyncWrapper

비동기 처리를 위한 미들웨어 입니다. `trxWrapper`는 트랜잭션을 생성하고 해당 트랜잭션에 대해 Commit & Rollback을 수행합니다. 생성한 트랜잭션을 비즈니스 로직 레이어로 넘겨주기 위해 `req` 객체의 `trx` 속성값에 할당합니다.

#### authenticator

사용자 인증 미들웨어입니다. 본 템플릿에서는 JWT를 인증 토큰으로 받아 처리합니다.

### Module Layers

Routing으로 애플리케이션 엔드포인트와 1:1 연결된 컨트롤러는 다수의 서비스를 사용할 수 있으며, 각 서비스는 트랜잭션 객체(`req.trx`)를 넘겨 받아 데이터베이스에 접근합니다.

```
Controller Layer <=> Service Layer(Data Access Layer)
```

#### Controller Layer: HTTP 통신

컨트롤러 레이어에서는 서비스 레이어을 호출해 비즈니스 로직을 수행하고 HTTP 통신을 처리합니다.

```js
// user.controller.js
const { createUser } = require('../services/user.service');
const { generateAuthTokens } = require('../services/token.service');
const { loginUserWithEmailAndPassword } = require('../services/auth.service');

const register = async (req, res) => {
  const userId = await createUser(req.trx, req.body);
  const tokens = await generateAuthTokens(req.trx, user);
  res.status(200).send({ userId, tokens });
};

const login = async (req, res) => {
  const { email, password } = req.body;
  const user = await loginUserWithEmailAndPassword(req.trx, email, password);
  const tokens = await generateAuthTokens(user);
  res.status(200).send({ user, tokens });
};

module.exports = {
  register,
  login,
};
```

#### Service Layer: 비즈니스 로직

서비스 레이어에서는 컨트롤러 레이어에서 넘겨받은 인자를 바탕으로 쿼리 빌더(`knex`)를 사용해 데이터베이스에 접근하고 비즈니스 로직을 수행한 뒤 결과를 컨트롤러 레이어로 반환합니다.

```js
// user.service.js
const { v4: uuid } = require('uuid'); // npm i uuid
const CustomError = require('../config/CustomError');
/**
 * Create a user
 * @param {Object} userBody
 * @returns {Promise<User>}
 */
const createUser = async (trx, userBody) => {
  // const [{ isEmailTake }] = await trx.raw(`SELECT IFNULL(COUNT(user_id), 0) FROM user WHERE email = ${userBody.email} AND deleted IS NULL LIMIT 1`);
  const { isEmailTaken } = await trx('user')
    .where({ email: userBody.email })
    .whereNull('deleted')
    .select({ isEmailTaken: trx.raw(`ifnull(count(user_id), 0)`) })
    .first();
  if (isEmailTaken) {
    throw new CustomError(400, 'Email already taken');
  }
  const userId = uuid();
  userBody.user_id = userId;
  await trx('user').insert(userBody);
  return userId;
};

module.exports = {
  createUser,
};
```

#### Validator

[Joi 라이브러리](https://joi.dev/api/?v=17.5.0)를 사용해 `req` 입력값에 대한 유효성 검증을 진행할 수 있습니다.

### Utils

자주 사용하는 기능들을 모아놓은 소스 코드입니다.

#### S3

- Presigned URL을 사용해 AWS S3에 이미지를 업로드(PUT)하거나, 다운로드(GET)합니다.
- 플랫폼마다 S3 객체 키를 설정하는 방식에 따라 `prefix`와 `fileName`에 대한 RegEx를 설정합니다. 아래 코드는 S3에 저장된 객체를 조회할 수 있는 Presigned URL을 요청하는 API의 라우터에 대해 RegEx를 설정한 예시입니다.

```js
router
  .route(
    '/presigned/raw/:prefix(user|post)/:fileName([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}.(heic|jpeg|jpg|png))'
  )
  /**
   * AWS S3 조회용 presigned URL 요청
   * @group commons - 공통
   * @route GET /presigned/raw/{prefix}/{fileName}
   * @param {string} prefix.path.required - 폴더
   * @param {string} fileName.path.required - 파일명
   */
  .get(asyncWrapper(getRawPresigned));
```

- 위 라우터에 대한 엔드포인트별 응답 여부는 아래와 같습니다.
  - [x] /v1/presigned/user/550e8400-e29b-41d4-a716-446655440000.jpg
    - 성공
  - [ ] /v1/presigned/test/550e8400-e29b-41d4-a716-446655440000.jpg
    - prefix가 유효하지 않음
  - [ ] /v1/presigned/post/550e8400-e29b-41d4-a716-446655440000.gif
    - 확장자가 유효하지 않음
  - [ ] /v1/presigned/user/550e8400-e29b-41d4-a716-446655440000
    - 확장자가 유효하지 않음
  - [ ] /v1/presigned/user/this_is_not_uuid.jpg
    - UUID 형식이 아님