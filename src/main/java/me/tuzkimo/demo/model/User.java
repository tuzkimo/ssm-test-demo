package me.tuzkimo.demo.model;

import java.time.Instant;
import javax.persistence.*;

public class User {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户 ID
     */
    private String uid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码 Hash
     */
    private String password;

    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private Boolean admin;

    /**
     * 是否已禁用
     */
    @Column(name = "is_banned")
    private Boolean banned;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Instant gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Instant gmtModified;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户 ID
     *
     * @return uid - 用户 ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置用户 ID
     *
     * @param uid 用户 ID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码 Hash
     *
     * @return password - 密码 Hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码 Hash
     *
     * @param password 密码 Hash
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取是否管理员
     *
     * @return is_admin - 是否管理员
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * 设置是否管理员
     *
     * @param admin 是否管理员
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * 获取是否已禁用
     *
     * @return is_banned - 是否已禁用
     */
    public Boolean getBanned() {
        return banned;
    }

    /**
     * 设置是否已禁用
     *
     * @param banned 是否已禁用
     */
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Instant getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Instant gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Instant getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Instant gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", uid='" + uid + '\'' +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", admin=" + admin +
            ", banned=" + banned +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            '}';
    }
}