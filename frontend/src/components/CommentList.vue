<template>
    <v-list 
        class="list"
    >
        <v-list-item
            v-for="comment of comments"
            :key="comment.id"
        >
            <div class="card" id="comment_card">
                <v-avatar color="info">
                    <v-icon icon="mdi-account-circle"></v-icon>
                </v-avatar>
                <div id="comment_text">
                    <h3>{{ comment.author }}</h3>
                    <p>{{ comment.text }}</p>
                </div>
                <div id="rating">
                    <p>Оценка: {{ comment.rating }} / 10</p>
                </div>
            </div>
        </v-list-item>
    </v-list>
</template>

<script>
    export default {
        name: "CommentList",
        data: () => ({
            comments: []
        }),
        props: {
            good_id: {
                type: Number,
                required: true
            }
        },
        methods: {
            async fetchComments() {
                this.$store.dispatch('fetchComments', this.good_id);
            }
        },
        mounted() {
            this.fetchComments();
        }
    }
</script>

<style lang="scss" scoped>
#comment_card {
    width: 60vw;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
#comment_text {
    display: flex;
    flex-direction: column;
    text-align: left;
}
</style>