<template>
  <view class="page_home">
    <cu-custom bgColor="bg-gradual-green1" :isBack="false">
      <block slot="content">首页</block>
    </cu-custom>

    <!-- banner -->
    <view class="ph-menu">
      <swiper
        class="ph-banner screen-swiper square-dot"
        :indicator-dots="true"
        :circular="true"
        :autoplay="true"
        interval="5000"
        duration="500"
      >
        <swiper-item
          class="ph-banner-item"
          v-for="(item, index) in swiperList"
          :key="index"
        >
          <image :src="item.url" v-if="item.type == 'image'"></image>
          <video
            :src="item.url"
            autoplay
            loop
            muted
            :show-play-btn="false"
            :controls="false"
            objectFit="cover"
            v-if="item.type == 'video'"
          ></video>
        </swiper-item>
      </swiper>
    </view>
    <!-- 菜单 -->
    <view class="ph-menu">
      <view class="bg-white">
        <view class="grid col-4 grid-square">
          <view
            @click="menuClickHandler(item.id)"
            class="bg-img"
            v-for="(item, index) in menus"
            :key="index"
            :style="[{ backgroundImage: 'url(' + menus[index] + ')' }]"
          >
            <navigator :url="item.page + '?title=' + item.txt">
              <view class="phm-item">
                <view class="img_view phm-item-image">
                  <image :src="item.icon" class="image"></image>
                </view>
                <text class="phm-item-txt">{{ item.txt }}</text>
              </view>
            </navigator>
          </view>
        </view>
      </view>
    </view>
    <!-- 校友分布 -->
    <view class="ph-menu mapBox" v-if="!state">
      <alumnusDistribution></alumnusDistribution>
      <image class="legend" src="../../static/home/legend.png" mode=""></image>
    </view>
    <view class="ph-menu">
      <scroll-view scroll-x class="bg-white nav" scroll-with-animation>
        <view
          class="cu-item cuItem"
          :class="index == tabCur ? 'text-green1' : ''"
          v-for="(item, index) in tabList"
          :key="index"
          @tap="tabSelect"
          :data-id="item.id"
        >
          {{ item.name }}
        </view>
      </scroll-view>

      <view
        class="phm-card cu-card case no-card"
        v-for="(item, index) in newsList"
        :key="index"
      >
        <navigator :url="'/pages/home/newsDetail/newsDetail?id=' + item.id">
          <newsItem :opts="item"></newsItem>
        </navigator>
      </view>
    </view>
    <!-- <view class="ph-menu">
			<view class="cu-bar bg-white solid-bottom">
				<view class="action">
					<text class="cuIcon-titles text-green1"></text> 校友分布
				</view>
			</view>
			<view class="ph-menu-map">
				<umap class="phm-map"></umap>
			</view>
		</view> -->
  </view>
</template>

<script>
import { dateUtil } from "@/utils/dateUtil.js";
import { getNewsList } from "@/api/news.js";
// import umap from "./map/map.vue"
import alumnusDistribution from "../alumnus/alumnusDistribution.vue";
import newsItem from "@/components/news-list/news-item.vue";
export default {
  components: {
    // umap,
    alumnusDistribution,
    newsItem,
  },
  data() {
    return {
      state: true,
      swiperList: [
        {
          id: 0,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner22x.png",
        },
        {
          id: 1,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner12x.png",
        },
        {
          id: 2,
          type: "image",
          url: "http://www.imapway.cn/Alumni/static/home/banner32x.png",
        },
      ],
      menus: [
        {
          icon: "http://www.imapway.cn/Alumni/static/home/dcxw2x.png",
          txt: "地测新闻",
          page: "/pages/home/news/news",
        },
        {
          id: "xyrz",
          icon: "http://www.imapway.cn/Alumni/static/home/yxxy2x.png",
          txt: "校友认证",
          page: "",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/xqzt2x.png",
          txt: "校友统计",
          page: "/pages/home/statistics/statistics",
        },
        {
          id: "ysjs",
          icon: "http://www.imapway.cn/Alumni/static/home/ysjs2x.png",
          txt: "院史介绍",
          page: "/pages/schoolHistory/schoolHistory",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/szll2x.png",
          txt: "师资力量",
          page: "/pages/teachers/teachers",
        },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/xyhz2x.png",
          txt: "校友合作",
          page: "/pages/cooperation/cooperation",
        },

        // {
        //   icon: "http://www.imapway.cn/Alumni/static/home/dlqq2x.png",
        //   txt: "点亮全球",
        //   page: "/pages/anniversary/footprint/footprint",
        // },
        {
          icon: "http://www.imapway.cn/Alumni/static/home/dlqq2x.png",
          txt: "优秀校友",
          page: "/pages/alumnus/goodMember",
        },
        {
          id: "xyjz",
          icon: "http://www.imapway.cn/Alumni/static/home/xyjz2x.png",
          txt: "校友捐赠",
          page: "",
        },
      ],
      tabCur: 0,
      tabList: [
        {
          name: "资讯",
          id: 0,
        },
        {
          name: "推荐",
          id: 1,
        },
      ],
      newsList: [
        {
          thumb: '["http://www.imapway.cn/Alumni/static/home/banner22x.png"]',
          title: "学生工作业务能力专题培训举办",
          contents:
            "我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。",
          createTime: "2020-10-18",
          viewCount: 23,
        },
        {
          thumb: '["http://www.imapway.cn/Alumni/static/home/banner32x.png"]',
          title: "正义天使 凯尔",
          contents: "",
          createTime: "2020-10-18",
          viewCount: 23,
        },
      ],
    };
  },
  onShow() {
    this.state = false;
    console.log("state", this.state);
  },
  onHide() {
    this.state = true;
  },
  onLoad(options) {
    this.getNewsListData();
    if (options.url) {
      let url = decodeURIComponent(options.url);
      console.log("url", url);
      uni.navigateTo({
        url: url,
      });
    }
  },
  methods: {
    getNewsListData(sort) {
      let param = {
        pageNo: 1,
        pageSize: 2,
        type: 0,
        sort: sort ? sort : "createTime",
      };
      getNewsList(param).then(data => {
        var [error, res] = data;
        if (res && res.data.success) {
          let ss = res.data.result.content;
          this.newsList = res.data.result.content;
        }
      });
    },
    formatDate(date) {
      return dateUtil.formatDate(date);
    },
    tabSelect(e) {
      this.tabCur = e.currentTarget.dataset.id;
      if (this.tabCur == 1) {
        this.getNewsListData("viewCount");
      } else {
        this.getNewsListData();
      }
    },
    menuClickHandler(value) {
      if (value && value == "xyjz") {
        uni.navigateToMiniProgram({
          appId: "wx57ef5751ef32a454",
          success(res) {
            // 打开成功
          },
        });
      }
      if (value && value == "xyrz") {
        // console.log('校友认证')
        //判断认证状态
        let isCertification = uni.getStorageSync("isCertification");
        if (isCertification) {
          uni.navigateTo({
            url: "/pages/personal/basicInfo/basicInfo",
          });
        } else {
          uni.navigateTo({
            url: "/pages/personal/basicInfo/certification",
          });
        }
      }
      // if (value && value == "ysjs") {
      //   uni.switchTab({
      //     url: "/pages/schoolHistory/schoolHistory",
      //   });
      // }
    },
  },
};
</script>

<style lang="scss" scoped>
.phm-card {
  border-bottom: 1rpx solid #e5dee5;
}
.screen-swiper {
  min-height: 300rpx;
}
.ph-menu {
  padding: 10px;
  margin-bottom: 10px;
  background: white;
  .cuItem {
    height: 72rpx;
    display: inline-block;
    line-height: 80rpx;
    margin: 0 6px;
    padding: 0;
    font-size: 16px;
  }
  .text-green1 {
    color: #00beb7;
    border-bottom: 2px solid #00beb7;
  }
}
.mapBox {
  position: relative;
  .legend {
    width: 50px;
    height: 60px;
    position: absolute;
    bottom: 30rpx;
    left: 40rpx;
	z-index: 999;
  }
}
.ph-banner {
  border-radius: 10px;
}

.bg-img {
  min-height: 65px;
  margin-bottom: 0rpx;
  padding-bottom: 0rpx !important;
}
.bg-white {
  margin: 0 10px;
}

.ph-banner-item {
  border-radius: 10px;
}

.phm-item {
  width: 100%;
  height: 100%;
  margin: 0 auto;
}

.phm-item-image {
  width: 40px;
  height: 40px;
  margin: 0 auto;
  position: relative;
}

.phm-item-txt {
  width: 56px;
  height: 30px;
  margin: 0 auto;
  position: relative;
  display: block;
  font-size: 14px;
}

.phm-zx-item {
  height: 50px;
  margin-top: 10px;
}

.phm-zx-content {
  position: absolute;
  left: 15px;
  right: 15px;
}

.phm-zx-view {
  margin-left: 50rpx;
}

.news-title {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ph-menu-map {
  width: 100%;
}

.phm-map {
  width: 100%;
}
</style>
