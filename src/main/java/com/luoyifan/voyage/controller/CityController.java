package com.luoyifan.voyage.controller;

import com.luoyifan.voyage.constants.CityOperationEnum;
import com.luoyifan.voyage.service.CityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author EvanLuo
 */
@RequestMapping("/city")
@Controller
public class CityController extends BaseController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public String city(Model model) {
        model.addAttribute("cityPrice", cityService.getPriceOfCreateCity());
        model.addAttribute("cityOperationList", cityService.listCityOperation());
        return forward(VOYAGE_VIEW);
    }

    @PostMapping
    public String city(Model model, CityOperationEnum cityOperation) {
        if (cityOperation == null) {
            return city(model);
        }
        model.addAttribute("cityOperation", cityOperation);
        switch (cityOperation) {
            case CREATE:
                return forward("/city/create");
            case TRADE:
                return forward("/city/trade");
            case BANK:
                return forward("/city/bank");
            case TAX_RATE:
                return forward("/city/taxRate");
            case ADMIN:
                return forward("/city/admin");
            case ATTACK:
                return forward("/city/attack");
            case NAME:
                return forward("/city/name");
            case DESCRIPTION:
                return forward("/city/description");
            default:
                return city(model);
        }
    }

    @PostMapping("/create")
    public String create(Model model,
                         String name) {
        if (StringUtils.isNotBlank(name)) {
            cityService.createCity(name);
            return redirectVoyage();
        }
        return city(model);
    }

    @PostMapping("/trade")
    public String trade(Model model, Long userPortCityGoodsId, Integer quantity, Long userPortCityShipId,Long shipId, Long userPortCityItemId) {
        if (userPortCityGoodsId != null) {
            cityService.purchaseGoods(userPortCityGoodsId, quantity);
        } else if (userPortCityShipId != null) {
            cityService.purchaseShip(userPortCityShipId);
        }else if(shipId!=null){
            cityService.purchaseSecrtShip(shipId);
        } else if (userPortCityItemId != null) {
            cityService.purchaseItem(userPortCityItemId);
        }
        model.addAttribute("userPortCityGoodsList", cityService.listPurchasableGoods());
        model.addAttribute("userPortCityShipList", cityService.listPurchasableShip());
        model.addAttribute("userPortCityItemList", cityService.listPurchasableItem());
        model.addAttribute("secretShipList", cityService.listPurchasableSecretShip());
        return city(model);
    }

    @PostMapping("/bank")
    public String bank(Model model, Integer depositAmount, Integer withdrawalAmount) {
        if (depositAmount != null) {
            cityService.deposit(depositAmount);
            return redirectVoyage();
        }
        if (withdrawalAmount != null) {
            cityService.withdraw(withdrawalAmount);
            return redirectVoyage();
        }
        model.addAttribute("depositAmount", cityService.getCurrentPortDepositAmount());
        return city(model);
    }

    @PostMapping("/taxRate")
    public String taxRate(Model model, Integer taxRate) {
        if (taxRate != null) {
            cityService.updateCityTaxRate(taxRate);
            return redirectVoyage();
        }
        return city(model);
    }

    @PostMapping("/name")
    public String updateName(Model model, String name) {
        if (StringUtils.isNotBlank(name)) {
            cityService.updateCityName(name);
            return redirectVoyage();
        }
        return city(model);
    }

    @PostMapping("/description")
    public String updateDescription(Model model, String description) {
        if (StringUtils.isNotBlank(description)) {
            cityService.updateCityDescription(description);
            return redirectVoyage();
        }
        return city(model);
    }

    @PostMapping("/admin")
    public String admin(Model model, Integer withdrawalAmount, Integer hp, Integer price, Integer quantity, Long userGoodsId, Long userShipId, Long userItemId, Integer cityPrice) {
        if (withdrawalAmount != null) {
            cityService.withdrawalCityMoney(withdrawalAmount);
        } else if (hp != null) {
            cityService.repairCity(hp);
        } else if (price != null) {
            if (userGoodsId != null && quantity != null) {
                cityService.storeGoodsToCity(userGoodsId, quantity, price);
            } else if (userShipId != null) {
                cityService.storeShipToCity(userShipId, price);
            } else if (userItemId != null) {
                cityService.storeItemToCity(userItemId, price);
            }
        } else if (cityPrice != null) {
            cityService.sellCity(cityPrice);
        }
        model.addAttribute("repairCost", cityService.getRepairCityCost());
        model.addAttribute("maxHp", cityService.getCityMaxHp());
        return city(model);
    }

    @PostMapping("/attack")
    public String attack(Model model, String type) {
        if ("0".equals(type)) {
            cityService.armyAttack();
            return redirectVoyage();
        }
        if ("1".equals(type)) {
            cityService.moneyAttack();
            return redirectVoyage();
        }
        model.addAttribute("attackCost", cityService.getCityMoneyAttackCost());
        model.addAttribute("attackDamage", cityService.getCityMoneyAttackDamage());
        return city(model);
    }
}
