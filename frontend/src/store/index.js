import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    jwt: null,
    auth_user_name: null,
    auth_email: null,
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
      let parser = function(list, category) {
        if(category.parent_id == null){
          list.push({id: category.id, name: category.name, childs: []});
          return;
        }
        // parent search
        for(let item of list){
          if(item.id == category.parent_id){
            item.childs.push({id: category.id, name: category.name, childs: []});
            return;
          }
          if(item.childs && item.childs.length != 0){
            // recursive call for childs
            parser(item.childs, category);
          }
        }
      }
      let categoryList = [];
      let stateCategories = state.categories;
      for(let category of stateCategories){ 
        parser(categoryList, category);
      }
      return categoryList;
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
          manufacturer: good.manufacturer,
          categories: good.categories,
          description: good.description,
          image_path: good.imagePath
        };
      });
    },
    setCommentsData(state, commentsData) {
      state.comments = commentsData.map(comment => {
        return {
          id: comment.id,
          author: `${comment.user.secondName} ${comment.user.firstName}`,
          text: comment.text,
          rating: comment.rating,
          image_path: comment.imagePath
        };
      });
    },
    setCategoriesData(state, categoriesData) {
      let mappedList = [{
        id: 0,
        name: 'Все товары',
        parent_id: null
      }];
      mappedList.push.apply(mappedList, categoriesData.map(category => {
        return {
          id: category.id,
          name: category.name,
          parent_id: category.parent_id
        }
      }));
      state.categories = mappedList;
    },
    addGood(state, good) {
      state.goods.push(good);
    },
    addCategory(state, category) {
      state.categories.push(category);
    },
    addComment(state, comment) {
      state.comments.push(comment);
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
    },
    setAuthData(state, data) {
      state.jwt = data.jwt;
      state.auth_email = data.auth_email;
      state.auth_user_name = data.auth_user_name;
    },
    logout(state){
      state.jwt = null;
      state.auth_user_name = null;
      state.auth_email = null;
      localStorage.removeItem('jwt')
    }
  },
  actions: {
      fetchGoods({ commit }, category) {
          const baseURL = "http://localhost:5000/api/goods";
          let headers = {Authorization: ''};
          if(this.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
          axios.get(baseURL, { params: { category: category }, headers: headers})
          .then(response => {
              commit("setGoodsData", response.data);
          })
          .catch(e => {
              console.log(e); 
          });
      },
      fetchComments({ commit }, good_id) {
        const baseURL = "http://localhost:5000/api/comments";
        let headers = {Authorization: ''};
          if(this.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
        axios.get(baseURL, { params: { good_id: good_id }, headers: headers})
        .then(response => {
            commit("setCommentsData", response.data);
        })
        .catch(e => {
            console.log(e); 
        });
      },
      sendComment({ commit }, comment) {
        const baseURL = "http://localhost:5000/api/comments";
        axios.post(baseURL)
        .then(response => {
            
        })
        .catch(e => {
            console.log(e); 
        });
      }, 
      fetchCategories({ commit }) {
          const baseURL = "http://localhost:5000/api/categories";
          let headers = {Authorization: ''};
          if(this.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
          axios.get(baseURL , {headers: headers })
          .then(response => {
              commit("setCategoriesData", response.data);
          })
          .catch(e => {
              console.log(e); 
          });
      },
      fetchAuthenticateUser({ commit }) {
        const baseURL = "http://localhost:5000/api/auth_user";
          let headers = {Authorization: ''};
          if(localStorage.getItem('jwt')) {
            headers.Authorization = 'Bearer ' + localStorage.getItem('jwt');
          }
          axios.get(baseURL , {headers: headers })
          .then(response => {
              const data = {
                jwt: localStorage.getItem('jwt'),
                auth_email: response.data.email,
                auth_user_name: response.data.username
              }
              commit("setAuthData", data);
          })
          .catch(e => {
              console.log(e); 
          });
      }
  },
  modules: {
  }
})
