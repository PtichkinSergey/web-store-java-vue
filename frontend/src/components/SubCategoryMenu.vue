<template>
    <v-menu>
      <template
        v-for="category in menuList"
      >
        <SubCategoryMenuVue
          v-if="category.childs && category.childs.length != 0"
          :key="category.id"
          :menuList="category.childs"
        />
        <v-list-item
          v-else
          :key="category.id"
          @click="changeCategory(category.id)"
        >
          <v-list-item-title>{{ category.name }}</v-list-item-title>
        </v-list-item>
      </template>
    </v-menu>
</template>

<script>
export default {
    data: () => ({
        
    }),
    props: () => ({
        menuList: {
            type: Object,
            default: () => {}
        }
    }),
    methods: {
      changeCategory(id) {
        this.$router.push({query: {category: id}});
      }
    },
}
</script>