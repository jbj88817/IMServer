package us.bojie.imbo.push.factory;

import org.hibernate.Session;

import us.bojie.imbo.push.bean.db.User;
import us.bojie.imbo.push.utils.Hib;
import us.bojie.imbo.push.utils.TextUtil;

public class UserFactory {

    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中的User信息
     *
     * @param account  账户
     * @param password 密码
     * @param name     用户名
     * @return User
     */
    public static User register(String account, String password, String name) {
        // 去除账户中的首位空格
        account = account.trim();
        // 处理密码
        password = encodePassword(password);

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);

        // 进行数据库操作
        // 首先创建一个会话
        Session session = Hib.session();
        // 开启一个事物
        session.beginTransaction();
        try {
            // 保存操作
            session.save(user);
            // 提交我们的事物
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            // 失败情况下需要回滚事物
            session.getTransaction().rollback();
            return null;
        }
    }

    private static String encodePassword(String password) {
        // 密码去除首位空格
        password = password.trim();
        // 进行MD5非对称加密，加盐会更安全，盐也需要存储
        password = TextUtil.getMD5(password);
        // 再进行一次对称的Base64加密，当然可以采取加盐的方案
        return TextUtil.encodeBase64(password);
    }
}
