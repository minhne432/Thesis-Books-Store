// static/js/scripts.js

document.addEventListener('DOMContentLoaded', () => {
    // Xử lý thêm vào giỏ hàng
    const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');
    const cartBadge = document.querySelector('.badge');

    addToCartButtons.forEach(button => {
        button.addEventListener('click', () => {
            let count = parseInt(cartBadge.textContent);
            cartBadge.textContent = count + 1;
            // Thêm logic gửi yêu cầu đến server để cập nhật giỏ hàng
            // Ví dụ: sử dụng fetch hoặc AJAX để thêm sản phẩm vào giỏ
            // Hiển thị thông báo hoặc popup xác nhận
            alert('Đã thêm sản phẩm vào giỏ hàng!');
        });
    });

    // Tự động đóng sidebar khi nhấn liên kết trên thiết bị nhỏ
    const sidebarLinks = document.querySelectorAll('#sidebar a');
    sidebarLinks.forEach(link => {
        link.addEventListener('click', () => {
            const sidebar = document.getElementById('sidebar');
            const bsCollapse = new bootstrap.Collapse(sidebar, {
                toggle: false
            });
            bsCollapse.hide();
        });
    });
});
