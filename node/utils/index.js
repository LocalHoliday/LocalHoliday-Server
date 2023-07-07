exports.ALPHANUM = [...'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'];
exports.ALPHABET = [...'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'];
exports.NUMBER = [...'0123456789'];
exports.SPECIAL = [...`\`~!@#$%^&*()-_=+{}[]|\\:;'",./?`];
exports.REGEX = {
  IMAGE: /^image\//,
  JWT: /^[\w\W]*\.[\w\W]*\.[\w\W]*$/,
  UUID: '[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}', // 라우트 경로 정규식
};

/**
 * @description 배열 안의 원소들을 무작위로 섞어주는 함수
 */
exports.shuffle = array => {
  for (let i = array.length - 1; i > 0; i--) {
    let j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
};

/**
 * @description 임의의 문자열을 생성하는 함수
 */
exports.getRandomString = strLength => {
  return [...Array(strLength)]
    .map(() => this.ALPHANUM[Math.trunc(Math.random() * this.ALPHANUM.length)])
    .join('');
};

/**
 * @description 임의의 숫자를 생성하는 함수
 */
exports.getRandomNumber = (strLength = 6) => {
  strLength = parseInt(strLength);
  if (isNaN(strLength)) return;
  return (
    Math.floor(Math.random() * (Math.pow(10, strLength) - Math.pow(10, strLength - 1))) +
    Math.pow(10, strLength - 1)
  );
};

/**
 * @description 임시 비밀번호를 생성하는 함수
 * 문자, 숫자, 특수문자 각 최소 하나 포함 8~16자 생성
 * 문자 2 : 숫자 1 : 특수문자 1 의 비율로 생성
 */
exports.getRandomPassword = strLength => {
  const alphaLength = Math.floor(strLength * 0.5);
  const numberLength = Math.floor(strLength * 0.25);

  const newAlpha = [...Array(alphaLength)]
    .map(() => this.ALPHABET[Math.floor(Math.random() * this.ALPHABET.length)])
    .join('');

  const newNumber = [...Array(numberLength)]
    .map(() => this.NUMBER[Math.floor(Math.random() * this.NUMBER.length)])
    .join('');

  const newSpecial = [...Array(numberLength)]
    .map(() => this.SPECIAL[Math.floor(Math.random() * this.SPECIAL.length)])
    .join('');

  const newPassword = newAlpha + newNumber + newSpecial;
  return this.shuffle([...newPassword]).join('');
};

/**
 * @description 인증코드 생성 함수
 */
exports.getRandomAuthCode = strLength => {
  return [...Array(strLength)]
    .map(() => this.NUMBER[Math.trunc(Math.random() * this.NUMBER.length)])
    .join('');
};

/**
 * @description 페이지네이션 정보를 반환하는 함수
 * @param {number} totalCount 전체 데이터 개수
 * @param {number} requestedPage 현재 페이지
 * @param {number} perPage 한 페이지에 보여줄 데이터 개수
 */
exports.getPageInfo = (totalCount = 0, requestedPage = 1, perPage = 10) => {
  const totalData = Number(totalCount);
  const currentPage = Number(requestedPage);
  const dataPerPage = Number(perPage);
  const totalPages = Math.ceil(totalData / perPage);
  const startRow = currentPage > 1 ? currentPage * perPage - perPage : 0;
  return {
    totalData, // the number of data
    currentPage, // current page number
    dataPerPage, // the number of data per page
    totalPages, // the number of total pages
    startRow, // SQL LIMIT offset
  };
};

/**
 * @description 현재 날짜와 오프셋 일수의 차이만큼의 일시를 KST 기준으로 반환하는 함수
 * @param {object} date Date 객체
 * @param {number} offset 오프셋 일수
 * @returns {string} YYYY-MM-DD hh:mm:ss
 */
exports.getKSTtoISOString = (date, offset = 0) => {
  date = date || new Date();
  date.setDate(date.getDate() + parseInt(offset));
  // const utcDate = date.getTime() + date.getTimezoneOffset() * 60 * 1000
  const kstOffset = 9 * 60 * 60 * 1000;
  return new Date(date.getTime() + kstOffset).toISOString().replace(/\..+/, '').replace(/T/, ' ');
};

/**
 * 날짜 형식에 맞춰 년/월/일 구분하는 함수
 * @param {string} date 'YYYY-MM-DD', 'YYYY-MM', 'YYYY'
 * @param {string} 'day', 'month', 'year', undefined
 */
exports.getDateType = date => {
  const dayPattern = /([0-2][0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[0-1])/;
  const monthPattern = /([0-2][0-9]{3})-(0[1-9]|1[0-2])/;
  const yearPattern = /([0-2][0-9]{3})/;
  if (dayPattern.test(date) === true) {
    return 'day';
  } else if (monthPattern.test(date) === true) {
    return 'month';
  } else if (yearPattern.test(date) === true) {
    return 'year';
  } else {
    return;
  }
};
