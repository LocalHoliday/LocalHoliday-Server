class CustomError extends Error {
  constructor(message, status = 400) {
    super(message);
    this.status = status;

    if (Error.captureStackTrace) {
      Error.captureStackTrace(this, CustomError);
    }
  }
}

module.exports = CustomError;
