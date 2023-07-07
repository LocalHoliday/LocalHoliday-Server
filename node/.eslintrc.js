module.exports = {
  root: true,
  parserOptions: {
    ecmaVersion: 2020,
  },
  env: {
    node: true,
    browser: false,
    es6: true,
  },
  extends: ['plugin:prettier/recommended'],
  plugins: ['prettier'],
  ignorePatterns: [],
  rules: {
    'no-console': ['warn', { allow: ['warn', 'error'] }],
    quotes: ['error', 'single', { allowTemplateLiterals: true }],
    'prettier/prettier': [
      'error',
      {
        singleQuote: true,
        semi: true,
        tabWidth: 2,
        trailingComma: 'es5',
        printWidth: 100,
        bracketSpacing: true,
        arrowParens: 'avoid',
        endOfLine: 'auto',
      },
    ],
  },
};
