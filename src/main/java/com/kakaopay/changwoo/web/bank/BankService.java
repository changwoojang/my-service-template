package com.kakaopay.changwoo.web.bank;

import com.kakaopay.changwoo.common.error.ErrorCodeType;
import com.kakaopay.changwoo.domain.code.PeriodEnum;
import com.kakaopay.changwoo.domain.code.BankEnum;
import com.kakaopay.changwoo.domain.code.WordEnum;
import com.kakaopay.changwoo.domain.model.bank.*;
import com.kakaopay.changwoo.util.AppException;
import com.kakaopay.changwoo.web.bank.dto.*;
import com.kakaopay.changwoo.web.bank.dto.result.BankAmountAverageResult;
import com.kakaopay.changwoo.web.bank.dto.result.BankListResult;
import com.kakaopay.changwoo.web.bank.dto.result.BankTopAmountResult;
import com.kakaopay.changwoo.web.bank.dto.result.SupplyAmountSumTotalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by changwooj111@gmail.com on 2019-08-13
 */
@Service
@Slf4j
public class BankService {

    static final String UNSUPPORTED_YEAR = "2017";

    @Autowired
    SupplyAmountRespository supplyAmountRespository;

    @Autowired
    SupplyAmountSumRespository supplyAmountSumRespository;

    @Autowired
    BankRepository bankRepository;

    @Transactional
    public BankAmountAverageResult getBankAmountAverageMinMax(BankEnum bankType){

        //각 년도를 찾아낸다.
        List<String> years = getDistinctYear();
        List<String> supportedYear = years.stream()
                .filter(result-> !UNSUPPORTED_YEAR.equals(result))
                .collect(Collectors.toList());

//        List<SupplyAmount> supplyAmountsByYear = sumSupplyAmountByYear(supportedYear);

        List<SupplyAmountSumEntity> supplyAmountSumEntities = supplyAmountSumRespository.findByYearNot(UNSUPPORTED_YEAR);
        List<SupplyAmount> supplyAmountSums = supplyAmountSumEntities.stream()
                .map(SupplyAmount::fromSupplyAmountSum)
                .collect(Collectors.toList());

        //각 년도별 각 기관별 평균 금액을 가져온다.
        return getAverageSupplyAmountByBank(supplyAmountSums, bankType);
    }

    @Transactional
    public BankTopAmountResult getTopSupplyAmount(){

        //각 년도별 지원금액의 합계를 가져온다.
//        List<SupplyAmount> supplyAmountByYear = sumSupplyAmountByYear(getDistinctYear());

        List<SupplyAmountSumEntity> supplyAmountSumEntities = supplyAmountSumRespository.findAll();
        List<SupplyAmount> supplyAmountSums = supplyAmountSumEntities.stream()
                .map(SupplyAmount::fromSupplyAmountSum)
                .collect(Collectors.toList());


        //각 년도별 각 기관별 가장 큰 금액을 가져온다.
        List<BankTopAmountResult> bankTopAmountResults = getTopSupplyAmountByBankAndYear(supplyAmountSums);

        //각 기관별 중에서 가장 큰 금액만 가져온다.
        return bankTopAmountResults.stream()
                .max(Comparator.comparing(BankTopAmountResult::getSupplyAmount))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public SupplyAmountSumTotalResult getSupplyAmount(String period){

        if(!PeriodEnum.YEAR.getPeriod().equals(period)){ throw new AppException(ErrorCodeType.PERIOD_NOT_FOUND); }

        //각 년도별 지원금액의 합계를 가져온다.
//        List<SupplyAmount> supplyAmountsByYear = sumSupplyAmountByYear(getDistinctYear());

        List<SupplyAmountSumEntity> supplyAmountSumEntities = supplyAmountSumRespository.findAll();
        List<SupplyAmount> supplyAmountSums = supplyAmountSumEntities.stream().map(SupplyAmount::fromSupplyAmountSum).collect(Collectors.toList());


        //각 년도별 지원 금액의 합계 저장해준다.
        List<SupplyAmountSumTotal> supplyAmountSumTotals = supplyAmountSums
                .stream()
                .map(SupplyAmountSum::fromSupplyAmountSumTotal)
                .collect(Collectors.toList());

        return SupplyAmountSumTotalResult.builder()
                .name(WordEnum.HOUSING_FINANCE_SUPPLY_STATUS.getWord())
                .supplyAmountSumTotal(supplyAmountSumTotals).build();
    }

    @Transactional
    public BankListResult getListOfBank(){

        // 은행 목록을 가져온다.
        List<BankEntity> bankEntities = bankRepository.findAll();
        List<Bank> bankList = bankEntities.stream()
                .map(Bank::fromBankEntity)
                .collect(Collectors.toList());

        // 결과값을 저장 한다.
        BankListResult bankListResult = new BankListResult();
        bankListResult.setName(WordEnum.LIST_OF_INSTITUTIONS.getWord());
        bankListResult.setData(bankList);

        return bankListResult;
    }

    public BankAmountAverageResult getAverageSupplyAmountByBank(List<SupplyAmount> supplyAmountByYear, BankEnum bankType){

        List<SupplyAmountAverage> supplyAmountAverages = supplyAmountByYear.stream()
                .map(result -> {
                    SupplyAmountAverage supplyAmountAverage = new SupplyAmountAverage();
                    int bankAmount = 0;
                    switch (bankType){
                        case CITY_BANK :
                            bankAmount = result.getCityBankAmount(); break;
                        case SHINHAN_BANK:
                            bankAmount = result.getShinhanBankAmount(); break;
                        case HANA_BANK:
                            bankAmount = result.getHanaBankAmount(); break;
                        case KB_BANK:
                            bankAmount = result.getKbBankAmount(); break;
                        case KEB_BANK:
                            bankAmount = result.getKebBankAmount(); break;
                        case ETC_BANK:
                            bankAmount = result.getEtcBankAmount(); break;
                        case NONGHYUP_BANK:
                            bankAmount = result.getNonghyupBankAmount(); break;
                        case WOORI_BANK:
                            bankAmount = result.getWooriBankAmount(); break;
                        default:
                            throw new AppException(ErrorCodeType.PERIOD_NOT_FOUND);
                    }
                    supplyAmountAverage.setAverage(bankAmount / 12.0);
                    supplyAmountAverage.setYear(result.getYear());
                    return  supplyAmountAverage;

                })
                .collect(Collectors.toList());

        //금액의 평균 최소값 구하기
        SupplyAmountAverageMinMax supplyAmountAverageMax = supplyAmountAverages.stream()
                .max(Comparator.comparing(SupplyAmountAverage::getAverage))
                .map(result -> {
                    SupplyAmountAverageMinMax bankTopAmountResult = SupplyAmountAverageMinMax.builder()
                            .amount(Math.round(result.getAverage()))
                            .year(result.getYear())
                            .build();
                    return bankTopAmountResult;
                })
                .orElseThrow(NoSuchElementException::new);

        //금액의 평균 최대값 구하기
        SupplyAmountAverageMinMax supplyAmountAverageMin = supplyAmountAverages.stream()
                .min(Comparator.comparing(SupplyAmountAverage::getAverage))
                .map(result -> {
                    SupplyAmountAverageMinMax bankTopAmountResult = SupplyAmountAverageMinMax.builder()
                            .amount(Math.round(result.getAverage()))
                            .year(result.getYear())
                            .build();
                    return bankTopAmountResult;
                })
                .orElseThrow(NoSuchElementException::new);

        List<SupplyAmountAverageMinMax> supplyAmountAverageMinMaxes = new ArrayList<>();
        supplyAmountAverageMinMaxes.add(supplyAmountAverageMin);
        supplyAmountAverageMinMaxes.add(supplyAmountAverageMax);

        // 결과값
        BankAmountAverageResult bankAmountAverageResult = new BankAmountAverageResult();
        bankAmountAverageResult.setSupport_amount(supplyAmountAverageMinMaxes);
        bankAmountAverageResult.setBank(bankType.getBankName());

        return bankAmountAverageResult;
    }
    public List<BankTopAmountResult> getTopSupplyAmountByBankAndYear(List<SupplyAmount> supplyAmountByYear){

        List<BankTopAmountResult> bankTopAmountResults = new ArrayList<>();
        List<BankEntity> bankEntities =  bankRepository.findAll();

        for(BankEntity bankEntity : bankEntities){
            BankTopAmountResult topBankAmount = supplyAmountByYear.stream()
                    .max(Comparator.comparing(SupplyAmount::getHanaBankAmount))
                    .map(result -> {
                        String bankName = "";
                        int bankAmount = 0;
                        switch (bankEntity.getBankEnum()){
                            case CITY_BANK :
                                bankName = BankEnum.CITY_BANK.getBankName();
                                bankAmount = result.getCityBankAmount();
                                break;
                            case SHINHAN_BANK:
                                bankName = BankEnum.SHINHAN_BANK.getBankName();
                                bankAmount = result.getShinhanBankAmount();
                                break;
                            case HANA_BANK:
                                bankName = BankEnum.HANA_BANK.getBankName();
                                bankAmount = result.getHanaBankAmount();
                                break;
                            case KB_BANK:
                                bankName = BankEnum.KB_BANK.getBankName();
                                bankAmount = result.getKbBankAmount();
                                break;
                            case KEB_BANK:
                                bankName = BankEnum.KEB_BANK.getBankName();
                                bankAmount = result.getKebBankAmount();
                                break;
                            case ETC_BANK:
                                bankName = BankEnum.ETC_BANK.getBankName();
                                bankAmount = result.getEtcBankAmount();
                                break;
                            case NONGHYUP_BANK:
                                bankName = BankEnum.NONGHYUP_BANK.getBankName();
                                bankAmount = result.getNonghyupBankAmount();
                                break;
                            case WOORI_BANK:
                                bankName = BankEnum.WOORI_BANK.getBankName();
                                bankAmount = result.getWooriBankAmount();
                                break;
                            default:
                                throw new AppException(ErrorCodeType.BANK_NOT_FOUND);
                        }
                        BankTopAmountResult bankTopAmountResult = BankTopAmountResult.builder()
                                .year(result.getYear())
                                .bank(bankName)
                                .supplyAmount(bankAmount)
                                .build();
                        return bankTopAmountResult;
                    })
                    .orElseThrow(NoSuchElementException::new);

            bankTopAmountResults.add(topBankAmount);
        }
        log.info("BankTopAmountResults : {}",bankTopAmountResults);
        return bankTopAmountResults;
    }

    public List<String> getDistinctYear(){
        return supplyAmountRespository.findDistinctYear();
    }
/*

    public List<SupplyAmount> sumSupplyAmountByYear(List<String> year){

        List<SupplyAmount> supplyAmountsByYear = new ArrayList<>();

        for(String eachYear : year){
            //각 년도별 지원금액을 찾는다.
            List<SupplyAmount> supplyAmounts = findSupplyAmountByYear(eachYear);

            //각 년도별 지원금액의 합계를 저장한다.
            SupplyAmount supplyAmount = SupplyAmount.builder()
                    .year(eachYear)
                    .wooriBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getWooriBankAmount).sum())
                    .shinhanBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getShinhanBankAmount).sum())
                    .nonghyupBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getNonghyupBankAmount).sum())
                    .kebBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getKebBankAmount).sum())
                    .kbBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getKbBankAmount).sum())
                    .housingCityFundAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getHousingCityFundAmount).sum())
                    .hanaBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getHanaBankAmount).sum())
                    .etcBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getEtcBankAmount).sum())
                    .cityBankAmount(supplyAmounts.stream().mapToInt(SupplyAmount::getCityBankAmount).sum()).build();

            supplyAmountsByYear.add(supplyAmount);
        }
        log.info("SupplyAmounts sum with year = {}", supplyAmountsByYear);
        return supplyAmountsByYear;
    }
*/

    public List<SupplyAmount> findSupplyAmountByYear(String eachYear){
        return supplyAmountRespository.findSupplyAmountEntitiesByYear(eachYear)
                .stream()
                .map(SupplyAmount::fromSupplyAmount)
                .collect(Collectors.toList());
    }

}
