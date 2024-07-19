# Описание доступных endpoints

1. POST /api/sign-up - регистрация пользователей
2. POST /api/sign-in - авторизация пользователей
3. GET /api/categories - получение списка всех доступных категорий
4. GET /api/categories/{id} - получение категории по id
5. GET /api/goods - получение списка товаров по заданной категории и с сортировкой
6. GET /api/goods/{id} - получение товара по id
7. PUT /api/{goodId}/category/{ctgId} - привязка товара к категории
7. GET /api/orders - получение списка всех заказов
8. GET /api/orders/{id} - получение заказа по id
9. POST /api/order-create - создание заказа
10. GET /api/auth_user - получение данных пользователя по jwt