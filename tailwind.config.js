/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/amigoing/**/*.cljs',
    'node_modules/flowbite-react/lib/esm/**/*.js',
  ],
  plugins: [
    // ...
    require('flowbite/plugin'),
  ],

}

