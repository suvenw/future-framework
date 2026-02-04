<!-- ✅ 使用语义化类名 -->
<view class="page-container">
    <!-- ✅ 组件使用 kebab-case -->
    <custom-nav-bar 
        title="{{navTitle}}" 
        background="{{navBgColor}}"
    />
    
    <!-- ✅ 合理使用数据绑定 -->
    <view class="user-card" wx:if="{{userInfo.nickName}}">
        <image class="avatar" src="{{userInfo.avatarUrl}}" mode="aspectFill" />
        <text class="username">{{userInfo.nickName}}</text>
    </view>
    
    <!-- ✅ 列表渲染使用 wx:for -->
    <view class="list-container">
        <view 
            class="list-item" 
            wx:for="{{listData}}" 
            wx:key="id"
            data-id="{{item.id}}"
            bind:tap="handleItemTap"
        >
            <text class="item-title">{{item.title}}</text>
            <text class="item-desc">{{item.description}}</text>
            <text class="item-time">{{item.createTime}}</text>
        </view>
    </view>
    
    <!-- ✅ 空状态处理 -->
    <view class="empty-state" wx:if="{{listData.length === 0}}">
        <image src="/assets/images/empty.png" class="empty-image" />
        <text class="empty-text">暂无数据</text>
    </view>
</view>