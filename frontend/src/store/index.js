import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    goods: [],
    categories: [],
    comments: [],
    basket: []
  },
  getters: {
    getGoodsTotalCount(state) {
      let totalCount = 0;
      for(let basket_good of state.basket){
        totalCount += basket_good.count_in_basket;
      }
      return totalCount;
    },
    getCategoriesJSON(state) {
      let categoryList = [];
      for(let category of state.categories){
        if(category.parent_id != null)
          continue;
        else{
          let categoryObj = {id: category.id, name: category.name, childs: []};
          for(let ctg of state.categories){
            if(ctg.parent_id == category.id){
              categoryObj.childs.push()
            }
          }
        }
      }
    }
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
    setCategoriesData(state, categoriesData) {
      state.categories = categoriesData.map(category => {
        return {
          id: category.id,
          name: category.name,
          parent_id: category.parent_id
        }
      })
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
      fetchGoods({ commit }, category) {
          const baseURL = "http://localhost:5000/api/goods";
          console.log("Storage: category id = ", category)
          axios.get(baseURL, { params: { category: category }})
          .then(response => {
              commit("setGoodsData", response.data);
          })
          .catch(e => {
              console.log(e); 
          });
      },
      fetchCategories({ commit }) {
        const baseURL = "http://localhost:5000/api/categories";
        axios.get(baseURL)
        .then(response => {
            commit("setCategoriesData", response.data);
        })
        .catch(e => {
            console.log(e); 
        });
    }
  },
  modules: {
  }
})
