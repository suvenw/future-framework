/* ✅ 使用 BEM 命名规范 */
.page-container {
    padding: 0;
    background-color: var(--page-bg-color);
}

.user-card {
    display: flex;
    align-items: center;
    padding: 32rpx;
    background: var(--card-bg-color);
    border-radius: 16rpx;
    margin: 32rpx;
}

.user-card__avatar {
    width: 96rpx;
    height: 96rpx;
    border-radius: 50%;
    margin-right: 24rpx;
}

.user-card__username {
    font-size: 32rpx;
    font-weight: 500;
    color: var(--text-primary);
}

/* ✅ 使用 CSS 变量管理主题 */
:root {
    --page-bg-color: #f5f5f5;
    --card-bg-color: #ffffff;
    --text-primary: #333333;
    --text-secondary: #666666;
    --brand-color: #07c160;
}

/* ✅ 响应式单位使用 rpx */
.list-item {
    padding: 32rpx;
    margin: 0 32rpx 24rpx;
    background: var(--card-bg-color);
    border-radius: 16rpx;
}

.list-item:last-child {
    margin-bottom: 0;
}