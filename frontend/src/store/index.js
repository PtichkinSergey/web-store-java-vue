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
        description: "It's smart phone. long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long description",
        short_description: "short description."
      },
      {
        id: 2,
        name: "pc",
        cost: 159000,
        count: 2,
        category_id: 3,
        description: "It's personal computer. long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long description",
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
    ],
    comments: [
      {
        id: 1,
        author: "sergey",
        good_id: 1,
        text: "abobaabobaaboaba",
        rating: 10
      },
      {
        id: 2,
        author: "sergey",
        good_id: 2,
        text: "abobaabobaaboaba",
        rating: 3
      } 
    ],
    basket: []
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
    },
    addGoodToBasket(state, good) {
      state.basket.push(good);
    },
    removeGoodFromBasket(state, id) {
      state.basket = state.basket.filter((basket_good) => basket_good.good.id != id);
    },
    increaseBasketGoodAmount(state, id) {
      for(basket_good of state.basket){
        if(basket_good.good.id == good.id) {
          basket_good.amount ++;
          return;
        }
      }
    },
    decreaseBasketGoodAmount(state, id) {
      for(basket_good of state.basket){
        if(basket_good.good.id == id) {
          basket_good.amount --;
          if(basket_good.amount < 1) {
            state.basket = state.basket.filter((basket_good) => basket_good.good.id != id);
          }
          return;
        }
      }
    }
  },
  actions: {
  },
  modules: {
  }
})
