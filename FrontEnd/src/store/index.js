import { createStore } from 'vuex'
import router from '@/router'
import axios from 'axios'

const api_url = `http://localhost:8080/api`

export default createStore({
  state: {
    isChecked: true
  },
  getters: {
  },
  mutations: {
    SET_CHECKED: (state) => (state.isChecked = false) 
  },
  actions: {
    // 중복체크
    checkDupilicateId ({ commit }, id) {
      const params = {
        userId: id
      }
      axios({
        method: 'get',
        // eslint-disable-next-line camelcase
        url: api_url + `/user/duplication/id`,
        params
      }).then(res => {
        if (res.data === true) {
          alert('중복된 아이디입니다!')
          commit('SET_CHECKED')
        } else {
          alert('사용가능한 아이디입니다!')
        }
        //409를 받으면 실행되는 코드
      }).catch((res) => {
        console.log(res)
      })
    },
    checkDupilicateEmail ({ commit }, email) {
      const params = {
        email: email
      }
      axios({
        method: 'get',
        url: api_url + `/user/duplication/email`,
        params
      }).then(res => {
        if (res.data === true) {
          alert('중복된 이메일입니다!')
          commit('SET_CHECKED')
        } else {
          alert('사용가능한 이메일입니다!')
        }
      }).catch((res) => {
      })
    },
    join ({ commit }, userJoin) {
      axios.post(api_url + `/user/join`, userJoin, {
      }).then(res => {
        if (this.state.isChecked === true) {
          alert('회원가입 성공')
        }
      }).catch((res) => {
        alert('아이디중복 또는 이메일중복을 확인해주세요.')
        this.state.isChecked = true
        // 가족정보로..
      })
    }
  },
  modules: {
  }
})
