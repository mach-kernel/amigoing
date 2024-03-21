/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/amigoing/**/*.cljs',
    './resources/public/index.html',
    'node_modules/flowbite-react/lib/esm/**/*.js',
  ],
  plugins: [
    // ...
    require('flowbite/plugin'),
  ]
}
