// import jwtDecode from 'jwt-decode'
// import { login, findById, updateUser } from '@/api/user.js'


/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true
      state.userInfo = userInfo
    }
  },
  actions: {
    // 로그인
    login ({ commit }, user) {
      const api_url = 'http://localhost:8080/api/user/login'
      const data = {
        userId: user.userId,
        password: user.password
      }
      axios.post(api_url, data, {
      })
        .then((res) => {
          console.log(res)
          // console.log(res.headers)
          if (res.status === 200) {
            // const jwtToken = res.headers['Set-Cookie']
            // console.log(jwtToken)
            localStorage.setItem('userId', data.userId)
            commit('SET_IS_LOGIN', true)
            commit('SET_IS_LOGIN_ERROR', false)
          }
        })
        .catch((err) => {
          console.log(err)
          alert('아이디와 비밀번호를 다시한번 확인해주세요.')
        })
    },
    // 사용자 정보 조회 (나중에 로그인 후 회원정보 요청 으로 변경)
    getUserInfo ({ commit }, userId) {
      const api_url = 'http://localhost:8080/api/user/'
      axios.get(api_url + userId)
        .then((res) => {
          console.log(res)
          // familyId가 있는 경우, 메인으로 이동
          if (res.status === 200 & res.data.familyId != null) {
            router.push({ name: 'feed' })
          }
          // familyId가 없는 경우
          else{
            // if (res.data.)
            router.push({ name: 'familyCode' })
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 아이디 찾기
    findId ({ commit }, userInfo) {
      const api_url = 'http://localhost:8080/api/user/findId'
      const params = {
        userName: userInfo.userName,
        email: userInfo.email
      }
      axios({
        url: api_url,
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 아이디가 전송되었습니다.') !== -1) {
            alert(res.data.msg)
            router.push({ name: 'login' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 비밀번호 찾기
    findPassword ({ commit }, userInfo) {
      const api_url = 'http://localhost:8080/api/user/findPw'
      const params = {
        userName: userInfo.userName,
        userId: userInfo.userId,
        email: userInfo.email
      }
      axios({
        url: api_url,
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 임시 비밀번호가 전송되었습니다.') !== -1) {
            alert(res.data.msg)
            router.push({ name: 'login' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }

    // async userConfirm ({ commit }, user) {
    //   console.log(user)
    //   await login(
    //     user,
    //     (response) => {
    //       console.log(response)
    //       if (response.data.code === 200) {
    //         const token = response.data['access-token']
    //         commit('SET_IS_LOGIN', true)
    //         commit('SET_IS_LOGIN_ERROR', false)
    //         sessionStorage.setItem('access-token', token)
    //       } else {
    //         commit('SET_IS_LOGIN', false)
    //         commit('SET_IS_LOGIN_ERROR', true)
    //       }
    //     },
    //     (response) => {
    //       console.log(response)
    //       commit('SET_IS_LOGIN', false)
    //       commit('SET_IS_LOGIN_ERROR', true)
    //     }
    //   )
    // },
    // getUserInfo ({ commit }, token) {
    //   const decodeToken = jwtDecode(token)
    //   console.log(decodeToken)
    //   findById(
    //     decodeToken.id,
    //     (response) => {
    //       console.log(response)
    //       if (response.data) {
    //         commit('SET_USER_INFO', response.data)
    //       } else {
    //         console.log('유저 정보 없음!!')
    //       }
    //     },
    //     (error) => {
    //       console.log(error)
    //     }
    //   )
    // },

    // updateUserInfo ({ commit }, user) {
    //   updateUser(
    //     user,
    //     (response) => {
    //       console.log(response)
    //       if (response.data === 1) {
    //         commit('SET_USER_INFO', user)
    //       }
    //     },
    //     (error) => {
    //       console.log(error)
    //     }
    //   )
    // }
  }
}

export default userStore
