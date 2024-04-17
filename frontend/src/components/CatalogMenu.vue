<template>
    <div class="text-center">
      <v-menu :location="bottom">
        <template v-slot:activator="{ props }">
          <v-btn
            color="primary"
            dark
            v-bind="props"
          >
            Каталог
          </v-btn>
        </template>
  
        <v-list>
          <v-list-item
            v-for="(item, index) in this.items"
            :key="index"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
</template>

<script>
export default {
    data: () => ({
        items: [{title: 'apple'}, {title: 'pompom'}]
    })
}
</script>