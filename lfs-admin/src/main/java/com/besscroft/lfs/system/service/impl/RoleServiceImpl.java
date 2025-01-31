package com.besscroft.lfs.system.service.impl;

import com.besscroft.lfs.entity.AuthRole;
import com.besscroft.lfs.system.repository.RoleRepository;
import com.besscroft.lfs.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author Bess Croft
 * @Time 2021/7/8 18:09
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<AuthRole> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<AuthRole> getRolePageList(Integer pageNum, Integer pageSize, String keyword) {
        return roleRepository.findAll(PageRequest.of(Objects.equals(pageNum, 0) ? 0 : pageNum - 1, pageSize));
    }

    @Override
    public AuthRole getRoleById(@NonNull Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(@NonNull AuthRole authRole) {
        // 假删除：0->删除状态；1->可用状态
        authRole.setDel(1);
        // 设置时间
        authRole.setCreateTime(LocalDateTime.now());
        roleRepository.save(authRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(@NonNull AuthRole authRole) {
        // 设置时间
        authRole.setCreateTime(LocalDateTime.now());
        roleRepository.save(authRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRoleById(@NonNull List<Long> ids) {
        roleRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeSwitch(boolean status, @NonNull Long id) {
        if (status) {
            roleRepository.changeSwitch(1, id);
        }
        roleRepository.changeSwitch(0, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleById(@NonNull Long userId, @NonNull Long roleId) {
        // 先删除原有的
        int i = roleRepository.deleteUserRoleRelationById(userId);
        if (i > 0) {
            roleRepository.insertUserRoleRelation(userId, roleId);
        }
    }

}
