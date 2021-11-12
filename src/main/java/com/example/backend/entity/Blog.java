package com.example.backend.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotBlank(message = "内容不能为空")
    private String content;
    private String firstPicture;
    private String flag;
    @Transient
    private String tagIds;
    private Integer views;
    private boolean canAppreciate;
    private boolean shareStatement;
    private boolean canComment;
    private boolean published;
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public void init() {
        tagIds = listTagIds(tags);
    }

    private String listTagIds(List<Tag> list){
        if(!list.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : list) {
                if(flag)
                    ids.append(",");
                else
                    flag = true;
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        return tagIds;
    }
}
