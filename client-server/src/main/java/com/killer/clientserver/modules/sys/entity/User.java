package com.killer.clientserver.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Wu QiaoSheng
 * @since 2019-08-24
 */
@Data
@Accessors(chain = true)
public class User {

    private static final long serialVersionUID = 1L;

    private String nickname;

    private String phone;

    /**
     * 0：男，1：女， 2：偏男性，3：偏女性，4：无性别，5未知
     */
    private Boolean avatar;

    private Integer gender;

    private String qqAccessToken;

    private String qqRefreshToken;

    private LocalDateTime qqTokenExpire;

    @TableField("qq_openId")
    private String qqOpenid;


}
