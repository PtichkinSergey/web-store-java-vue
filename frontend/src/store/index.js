import { createStore } from 'vuex'

export default createStore({
  state: {
    goods: [
      {
        id: 1,
        name: "smartphone xiaomi 12x",
        cost: 39000,
        count: 15,
        category_id: 2,
        description: "It's smart phone.",
        short_description: "short description."
      },
      {
        id: 2,
        name: "pc",
        cost: 159000,
        count: 2,
        category_id: 3,
        description: "It's personal computer.",
        short_description: "short description."
      }
    ],
    categories: [
      {
        id: 1,
        name: "digital technology",
        parent: ""
      },
      {
        id: 2,
        name: "smartphone",
        parent_id: 1
      },
      {
        id: 3,
        name: "pc",
        parent_id: 1
      }
    ]
  },
  getters: {

  },
  mutations: {
    addGood(state, good) {
      state.goods.push(good);
    },
    addCategory(state, category) {
      state.categories.push(category);
    },
    removeGood(state, id) {
      state.goods = state.goods.filter((good) => good.id != id);
    },
    removeCategory(state, id) {
      state.categories = state.categories.filter((category) => category.id != id);
    }
  },
  actions: {
  },
  modules: {
  }
})
