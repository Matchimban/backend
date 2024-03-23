package com.project.matchimban.api.menu.service.impl;

import com.project.matchimban.api.menu.dto.dto.MenuCreateRequest;
import com.project.matchimban.api.menu.dto.dto.MenuReadResponse;
import com.project.matchimban.api.menu.dto.entity.Menu;
import com.project.matchimban.api.menu.dto.entity.MenuImage;
import com.project.matchimban.api.menu.repository.MenuImageRepository;
import com.project.matchimban.api.menu.repository.MenuRepository;
import com.project.matchimban.api.menu.service.MenuService;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.modules.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;

    private final S3Service s3Service;

    @Transactional
    public void createMenu(Restaurant restaurant, MenuCreateRequest request) {
        Menu menu = Menu.createMenu(restaurant, request);
        menuRepository.save(menu);

        for (MultipartFile image : request.getImages()) {
            MenuImage menuImage = createMenuImage(image, menu);
            menu.addImage(menuImage);
            menuImageRepository.save(menuImage);
        }
    }

    public MenuImage createMenuImage(MultipartFile image, Menu menu) {
        Optional<FileInfo> fileInfo = s3Service.saveFile(image);

        if (fileInfo.isEmpty())
            throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

        return MenuImage.createMenuImage(fileInfo.get(), menu);
    }

    public List<MenuReadResponse> getMenus(Restaurant restaurant) {
        List<MenuReadResponse> responses = new ArrayList<>();

        List<Menu> menus = menuRepository.findByRestaurantId(restaurant);
        for (Menu menu : menus) {

            responses.add(MenuReadResponse.createMenuReadResponse(menu, "imageURL 자리"));
        }
        return responses;
    }
}