package com.gerhardrvv.githubsearch.feature.search.data.remote.query

object GraphQLQueries {
    const val SEARCH_ACCOUNTS = """
        query SearchAccounts(${'$'}query: String!, ${'$'}resultLimit: Int) {
          search(query: ${'$'}query, type: USER, first: ${'$'}resultLimit) {
            edges {
              node {
                ... on Organization {
                  id
                  login
                  name
                  avatarUrl
                  repositories {
                    totalCount
                  }
                }
                ... on User {
                  id
                  login
                  name
                  avatarUrl
                  repositories {
                    totalCount
                  }
                }
              }
            }
          }
        }
    """
}
