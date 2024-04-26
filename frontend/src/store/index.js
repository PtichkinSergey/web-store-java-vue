import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    goods: [
      {
        id: 1,
        name: "smartphone xiaomi 12x",
        cost: 39000,
        count: 15,
        category_id: 1,
        description: "It's smart phone. long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long description",
      },
      {
        id: 2,
        name: "pc",
        cost: 159000,
        count: 2,
        category_id: 2,
        description: "It's personal computer. long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long \
        long long long long long long long long long long long long long long long long long long long long long long long long description",
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
    setGoodsData(state, goodsData) {
      state.goods = goodsData.map(good => {
        return {
          id: good.id,
          name: good.name,
          cost: good.cost,
          count: good.count,
          categories: good.categories,
          description: good.description,
        };
      });
    },
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
      if(!state.basket.includes((basket_good) => basket_good.good.id == good.id)) {
        let basket_good = {good: good, count_in_basket: 1, selected: true};
        state.basket.push(basket_good);
      }
    },
    removeGoodFromBasket(state, id) {
      state.basket = state.basket.filter((basket_good) => basket_good.good.id != id);
    },
    increaseBasketGoodCount(state, id) {
      for(let basket_good of state.basket){
        if(basket_good.good.id == id) {
          basket_good.count_in_basket ++;
          return;
        }
      }
    },
    decreaseBasketGoodCount(state, id) {
      for(let basket_good of state.basket){
        if(basket_good.good.id == id) {
          basket_good.count_in_basket --;
          if(basket_good.count_in_basket < 1) {
            state.basket = state.basket.filter((basket_good) => basket_good.good.id != id);
          }
          return;
        }
      }
    }
  },
  actions: {
      fetchGoods({ commit }) {
          const baseURL = "http://localhost:5000/api/goods";
          axios.get(baseURL, { params: { category: 0 }})
          .then(response => {
              commit("setGoodsData", res.data);
          })
          .catch(e => {
              console.log(e); 
          });
      }
  },
  modules: {
  }
})
