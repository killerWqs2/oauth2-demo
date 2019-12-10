package com.killer.clientserver.modules.sys.service.impl;

import com.killer.clientserver.modules.sys.entity.User;
import com.killer.clientserver.modules.sys.mapper.UserMapper;
import com.killer.clientserver.modules.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu QiaoSheng
 * @since 2019-08-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
