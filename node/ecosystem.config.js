module.exports = {
  apps: [
    {
      name: 'PROJECT_NAME',
      script: './server.js',
      watch: false,
      ignore_watch: ['logs', '.env', 'node_modules'],
      env_production: {
        PORT: 8000,
        NODE_ENV: 'production',
      },
      env_development: {
        PORT: 8000,
        NODE_ENV: 'development',
      },
      wait_ready: true,
      listen_timeout: 50000,
      kill_timeout: 5000,
    },
  ],
};
