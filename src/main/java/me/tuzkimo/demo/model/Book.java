package me.tuzkimo.demo.model;

import java.util.Date;
import javax.persistence.*;

public class Book {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 10 位国际标准书号
     */
    private String isbn10;

    /**
     * 13 位国际标准书号
     */
    private String isbn13;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者名
     */
    private String author;

    /**
     * 出版社名
     */
    private String publisher;

    /**
     * 出版日期
     */
    @Column(name = "pub_date")
    private Date pubDate;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

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
     * 获取10 位国际标准书号
     *
     * @return isbn10 - 10 位国际标准书号
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     * 设置10 位国际标准书号
     *
     * @param isbn10 10 位国际标准书号
     */
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    /**
     * 获取13 位国际标准书号
     *
     * @return isbn13 - 13 位国际标准书号
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * 设置13 位国际标准书号
     *
     * @param isbn13 13 位国际标准书号
     */
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     * 获取书名
     *
     * @return title - 书名
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置书名
     *
     * @param title 书名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取作者名
     *
     * @return author - 作者名
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者名
     *
     * @param author 作者名
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取出版社名
     *
     * @return publisher - 出版社名
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置出版社名
     *
     * @param publisher 出版社名
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 获取出版日期
     *
     * @return pub_date - 出版日期
     */
    public Date getPubDate() {
        return pubDate;
    }

    /**
     * 设置出版日期
     *
     * @param pubDate 出版日期
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}