import { defineConfig } from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'none',
  },
  proxy:{
    '/api': {
      target: 'http://localhost:9000',
      pathRewrite: { '^/api': '' },
      changeOrigin: true
    }
  },
  routes: [
    { path: '/', component: '@/pages/index' },
  ],
  fastRefresh: {},
});
