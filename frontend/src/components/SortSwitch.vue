<template>
    <a 
        v-if="this.sort_mode == 'descending'"
        @click="changeMode"
        class="link"
    >
        По убыванию
        <v-icon>mdi-sort-descending</v-icon>
    </a>
    <a 
        v-else
        @click="changeMode"
        class="link"
    >
        По возрастанию
        <v-icon>mdi-sort-ascending</v-icon>
    </a>
</template>

<script>
export default {
    data: () => ({
        sort_mode: 'ascending'
    }),
    methods: {
        changeMode() {
            if(this.sort_mode == 'ascending') {
                this.sort_mode = 'descending';
            }
            else {
                this.sort_mode = 'ascending';
            }
            this.$store.commit('changeSortMode');
            this.fetchGoods();
        },
        async fetchGoods() {
            let category = 0;
            if(this.$route.query.category) {
                category = this.$route.query.category;
            }
            this.$store.dispatch('fetchGoods', category);
        },
    },
    mounted() {
        console.log(this.$store.state.sort_mode)
        this.sort_mode = this.$store.state.sort_mode;
    }
}
</script>