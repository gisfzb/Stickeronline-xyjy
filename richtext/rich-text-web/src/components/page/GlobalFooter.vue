<template>
  <div class="footer">
    <div class="links">
      <a href="http://www.mapway.net/" target="_blank">Sticker 首页</a>
        <a-icon type="github"/>
      </a>
    </div>
    <div class="copyright">
      Copyright
      <a-icon type="copyright"/>
      2020 <span>Sticker 出品</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "LayoutFooter"
  }
</script>

<style lang="scss" scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;

    .links {
      margin-bottom: 8px;

      a {
        color: rgba(0, 0, 0, .45);

        &:hover {
          color: rgba(0, 0, 0, .65);
        }

        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
    }
  }
</style>