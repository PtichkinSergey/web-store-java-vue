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
    basket: [],
    sort_mode: 'ascending'
  },
  getters: {
    // Возвращает категорию товаров по переданному id
    getCategoryById: (state) => (id) => {
      if(state.categories.length < 1){
        return null;
      }
      if(!id) {
        return 'Все товары';
      }
      return state.categories.filter((category) => category.id == id)[0].name;
    },
    // Возвращает количество товаров, пренадлижащих категории
    getGoodsCountByCategory: (state) => (id) => {
      if(state.goods.length < 1) {
        return 0;
      }
      if(!id || id == 0) {
        return state.goods.length;
      }
      return state.goods.filter((good) => good.categories.filter((ctg) => ctg.id == id).length > 0).length;
    },
    // Возвращает полный путь до категории, начиная с корня через вложенные категории
    getPathToCategory: (state) => (id) => {
      if(state.categories.length == 0) {
        return null;
      }
      if(!id || id == 0) {
        return null;
      }
      let category = state.categories.find((ctg) => ctg.id == id);
      let path_to_category = [];
      path_to_category.push(category)
      while(category.parent_id != null) {
        category = state.categories.find((ctg) => ctg.id == category.parent_id)
        path_to_category.unshift(category)
      }
      return path_to_category
    },
    // Общее количество товаров в корзине
    getBasketTotalSize(state) {
      let totalSize = 0;
      for(let basket_good of state.basket) {
          if(basket_good.selected) {
            totalSize += basket_good.count_in_basket;
          }
      }
      return totalSize;
    },
    // Общая стоимость всех товаров без учёта скидок
    getBasketTotalCost(state) {
      let cost = 0;
      for(let basket_good of state.basket) {
          if(basket_good.selected) {
              cost += basket_good.good.cost * basket_good.count_in_basket;
          }
      }
      return cost;
    },
    // Общая величина скидки по всем товарам в корзине
    getTotalDiscount(state) {
      let discount = 0;
      for(let basket_good of state.basket) {
          if(basket_good.selected) {
            discount += basket_good.good.cost * basket_good.count_in_basket * (state.auth_email ? basket_good.good.discount : 0);
          }
      }
      return discount;
    },
    // Проверяет наличие товара в корзине
    getBasketContainsGood: (state) => (id) => {
      for(let basket_good of state.basket) {
        if(basket_good.good.id == id) {
          return true;
        }
      }
      return false;
    },
    // Формирует дерево категорий в формате JSON 
    getCategoriesJSON(state) {
      let parser = function(list, category) {
        if(category.parent_id == null){
          list.push({id: category.id, name: category.name, childs: []});
          return;
        }
        // ищем родителя
        for(let item of list){
          if(item.id == category.parent_id){
            item.childs.push({id: category.id, name: category.name, childs: []});
            return;
          }
          if(item.childs && item.childs.length != 0){
            // рекурсивный вызов для дочерних узлов
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
    },
    // Авторизован ли пользователь
    getUserLoggedIn(state) {
      return state.auth_user_name != null && state.auth_email != null && state.jwt != null;
    }
  },
  mutations: {
    setGoodsData(state, goodsData) {
      state.goods = goodsData.map(good => {
        return {
          id: good.id,
          name: good.name,
          cost: good.cost,
          discount: good.discount,
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
          parent_id: category.parentId
        }
      }));
      state.categories = mappedList;
    },
    addComment(state, comment) {
      state.comments.push(comment);
    },
    //добавление товара в корзину
    addGoodToBasket(state, good) {
      if(state.basket.filter((basket_good) => basket_good.good.id == good.id).length < 1) {
        state.basket.push({good: good, count_in_basket: 1, selected: true});
        localStorage.setItem('basket', JSON.stringify(state.basket));
      }
    },
    // удаление товара из корзины
    removeGoodFromBasket(state, id) {
      state.basket = state.basket.filter((basket_good) => basket_good.good.id != id)
      if(state.basket.length < 1) {
        localStorage.removeItem('basket');
      }
      else {
        localStorage.setItem('basket', JSON.stringify(state.basket));
      }
    },
    setAuthData(state, data) {
      state.jwt = data.jwt;
      state.auth_email = data.auth_email;
      state.auth_user_name = data.auth_user_name;
    },
    setBasketData(state, data) {
      state.basket = data;
    },
    // выход из аккаунта
    logout(state){
      state.jwt = null;
      state.auth_user_name = null;
      state.auth_email = null;
      localStorage.removeItem('jwt')
    },
    // переключение типа сортировки товаров в каталоге
    changeSortMode(state) {
      if(state.sort_mode == 'descending') {
        state.sort_mode = 'ascending';
      }
      else {
        state.sort_mode = 'descending';
      }
    },
    // Запрос на создание заказа
    executeOrder(state) {
      const baseURL = "http://localhost:5000/api/order-create";
      let headers = {Authorization: ''};
      if(this.state.jwt) {
        headers.Authorization = 'Bearer ' + this.state.jwt;
      }
      axios.post(baseURL, {data: state.basket, headers: headers})
      .then(response => {
          alert(response.data);
      })
      .catch(e => {
          console.log(e); 
      });
    }
  },
  actions: {
      fetchGoods({ commit }, category) {
          const baseURL = "http://localhost:5000/api/goods";
          let headers = {Authorization: ''};
          if(this.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
          axios.get(baseURL, { params: { category: category, sort: this.state.sort_mode}, headers: headers})
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
      fetchBasket({ commit }) {
        let data = JSON.parse(localStorage.getItem('basket'));
        commit("setBasketData", data);
      },
      // Восстановление данных авторизованного пользователя, при наличии корректного jwt в localStorage
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
