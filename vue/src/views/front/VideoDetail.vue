<template>
  <div class='demo'>
    <video-player class="video-player vjs-custom-skin"
                  ref="videoPlayer"
                  :playsinline="true"
                  @ready="playerReadied"
                  @pause="onPlayerPause($event)"
                  @timeupdate = "onPlayerTimeupdate($event)"
                  :options="playerOptions">
    </video-player>
  </div>
</template>

<script>
import request from "@/utils/request";
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'


export default {
  name: "VideoDetail",
  data() {
    return {
      // video:{},
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
        autoplay: false, // 如果为true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 是否视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'en',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [{
          type: "video/mp4", // 类型
          src: '' // url地址
        }],
        poster: '', // 封面地址
        notSupportedMessage: 'Can not play!', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true, // 当前时间和持续时间的分隔符
          durationDisplay: true, // 显示持续时间
          remainingTimeDisplay: false, // 是否显示剩余时间功能
          fullscreenToggle: true // 是否显示全屏按钮
        },
        currentTime: null
      }
    }
  },
  created() {
    let id = this.$route.query.id
    console.log(id)
    request.get("/front/video/" + id).then(res => {
      console.log(res.data.url)
      this.playerOptions.sources[0].src = res.data.url

    })
  },
  methods: {
    // VideoDetail: function (id) {
    //   this.playerOptions.sources[0]['src'] = serverIp + this.$apis.common.getFile+'?id=' + id;
    // },
    /* 获取视频播放进度 */
    onPlayerTimeupdate(player) {
      this.currentTime = player.cache_.currentTime
    },
    /* 设置视频开始的进度 */
    playerReadied(player) {
      player.currentTime(this.currentTime)
    },
    // 暂停回调   将视频播放的时间保存
    onPlayerPause(player) {
      console.log('player pause currentTime!', player.cache_.currentTime);
      let that = this;
      //TODO   存储player.currentTime 是当前暂停时间    用于下次断续播放

    },

  },
  // beforeMount() {
  //   let id = this.$route.query.id;
  //   if (id != undefined && id != null) {
  //     //编辑
  //     localStorage.setItem("videoId", id);
  //     this.VideoDetail(id);
  //   } else {
  //     id = localStorage.getItem("videoId");
  //     if (id != undefined && id != null) {
  //       this.VideoDetail(id);
  //     } else {
  //       localStorage.removeItem("videoId");
  //     }
  //   }
  // }
}
</script>

<style scoped>
.demo{
  padding-top: 30px;
}

.demo >>> .vjs-progress-control{
  pointer-events: none;
}
</style>