import { createStore } from 'vuex'; // Import `createStore` từ Vuex 4

export default createStore({
  state: {
    isLoggedIn: false
  },
  mutations: {
    setLoginStatus(state, status) {
      state.isLoggedIn = status;
    }
  },
  actions: {
    login({ commit }) {
      commit('setLoginStatus', true);
    },
    logout({ commit }) {
      commit('setLoginStatus', false);
    }
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn
  }
});
