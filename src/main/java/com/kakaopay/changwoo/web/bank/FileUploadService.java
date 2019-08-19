package com.kakaopay.changwoo.web.bank;

import com.kakaopay.changwoo.common.error.ErrorCodeType;
import com.kakaopay.changwoo.domain.code.BankEnum;
import com.kakaopay.changwoo.domain.code.FileExtensionEnum;
import com.kakaopay.changwoo.domain.model.bank.*;
import com.kakaopay.changwoo.util.AppException;
import com.kakaopay.changwoo.web.bank.dto.SupplyAmount;
import com.kakaopay.changwoo.web.bank.dto.result.FileUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Service
@Slf4j
public class FileUploadService {

    @Autowired
    FileUploadRecordRepository fileUploadRecordRepository;

    @Autowired
    SupplyAmountRespository supplyAmountRespository;

    @Autowired
    SupplyAmountSumRespository supplyAmountSumRespository;

    @Autowired
    BankRepository bankRepository;
/*
    @Autowired
    BankWooriAmountRepository bankWooriAmountRepository;

    @Autowired
    BankHanaAmountRepository bankHanaAmountRepository;

    @Autowired
    BankShinhanAmountRepository bankShinhanAmountRepository;

    @Autowired
    BankKbAmountRepository bankKbAmountRepository;

    @Autowired
    BankKebAmountRepository bankKebAmountRepository;

    @Autowired
    BankEtcAmountRepository bankEtcAmountRepository;

    @Autowired
    BankNongHyupAmountRepository bankNongHyupAmountRepository;

    @Autowired
    BankCityAmountRepository bankCityAmountRepository;

    @Autowired
    BankAmountRepository bankAmountRepository;

    private void addAllBankAmount(String year, String month, int kbBankAmount, int wooriBankAmount, int shinhanBankAmount, int cityBankAmount,
                                  int hanaBankAmount, int nonghyupBankAmount, int kebBankAmount, int etcBankAmount){

        BankWooriAmountEntity bankWooriAmount = BankWooriAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.WOORI_BANK.getBankName())
                .amount(wooriBankAmount).build();
        bankWooriAmountRepository.save(bankWooriAmount);

        BankHanaAmountEntity bankHanaAmount = BankHanaAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.HANA_BANK.getBankName())
                .amount(hanaBankAmount).build();
        bankHanaAmountRepository.save(bankHanaAmount);

        BankCityAmountEntity bankCityAmount = BankCityAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.CITY_BANK.getBankName())
                .amount(cityBankAmount).build();
        bankCityAmountRepository.save(bankCityAmount);

        BankKbAmountEntity bankKbAmount = BankKbAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.KB_BANK.getBankName())
                .amount(kbBankAmount).build();
        bankKbAmountRepository.save(bankKbAmount);

        BankKebAmountEntity bankKebAmount = BankKebAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.KEB_BANK.getBankName())
                .amount(kebBankAmount).build();
        bankKebAmountRepository.save(bankKebAmount);

        BankNonghyupAmountEntity bankNonghyupAmount = BankNonghyupAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.NONGHYUP_BANK.getBankName())
                .amount(nonghyupBankAmount).build();
        bankNongHyupAmountRepository.save(bankNonghyupAmount);

        BankShinhanAmountEntity bankShinhanAmount = BankShinhanAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.SHINHAN_BANK.getBankName())
                .amount(shinhanBankAmount).build();
        bankShinhanAmountRepository.save(bankShinhanAmount);

        BankEtcAmountEntity bankEtcAmount = BankEtcAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.ETC_BANK.getBankName())
                .amount(etcBankAmount).build();
        bankEtcAmountRepository.save(bankEtcAmount);
    }*/
    private SupplyAmountEntity parsingFromUploadFile(String eachFiles){

        String[] delimiteFileRecords = eachFiles.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        // TODO record는 무조건 integer이므로 아닐 경우 저장 하지 않는다.
        String year = delimiteFileRecords[0];
        String month = delimiteFileRecords[1];

        int housingAmount = Integer.parseInt(delimiteFileRecords[2].replaceAll("[^0-9]",""));
        int kbBankAmount = Integer.parseInt(delimiteFileRecords[3].replaceAll("[^0-9]",""));
        int wooriBankAmount = Integer.parseInt(delimiteFileRecords[4].replaceAll("[^0-9]",""));;
        int shinhanBankAmount = Integer.parseInt(delimiteFileRecords[5].replaceAll("[^0-9]",""));
        int cityBankAmount = Integer.parseInt(delimiteFileRecords[6].replaceAll("[^0-9]",""));
        int hanaBankAmount = Integer.parseInt(delimiteFileRecords[7].replaceAll("[^0-9]",""));
        int nonghyupBankAmount = Integer.parseInt(delimiteFileRecords[8].replaceAll("[^0-9]",""));
        int kebBankAmount = Integer.parseInt(delimiteFileRecords[9].replaceAll("[^0-9]",""));
        int etcBankAmount =Integer.parseInt(delimiteFileRecords[10].replaceAll("[^0-9]",""));

        SupplyAmountEntity uploadFileRecord = SupplyAmountEntity.builder()
                .year(year)
                .month(month)
                .housingCityFund(housingAmount).kbBank(kbBankAmount)
                .wooriBank(wooriBankAmount).shinhanBank(shinhanBankAmount)
                .cityBank(cityBankAmount).hanaBank(hanaBankAmount)
                .nonghyupBank(nonghyupBankAmount).kebBank(kebBankAmount).etcBank(etcBankAmount)
                .build();

        /*addAllBankAmount(year,month, kbBankAmount,wooriBankAmount,shinhanBankAmount,cityBankAmount
                ,hanaBankAmount,nonghyupBankAmount,kebBankAmount,etcBankAmount);*/

        /*
        List<BankAmountEntity> bankAmountEntities = new ArrayList<>();

        // 각 기관별 금액을 저장해준다.
        BankAmountEntity bankWooriAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.WOORI_BANK.getBankName())
                .amount(wooriBankAmount).build();
        bankAmountEntities.add(bankWooriAmount);

        BankAmountEntity bankHanaAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.HANA_BANK.getBankName())
                .amount(hanaBankAmount).build();
        bankAmountEntities.add(bankHanaAmount);

        BankAmountEntity bankCityAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.CITY_BANK.getBankName())
                .amount(cityBankAmount).build();
        bankAmountEntities.add(bankCityAmount);

        BankAmountEntity bankKbAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.KB_BANK.getBankName())
                .amount(kbBankAmount).build();
        bankAmountEntities.add(bankKbAmount);

        BankAmountEntity bankKebAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.KEB_BANK.getBankName())
                .amount(kebBankAmount).build();
        bankAmountEntities.add(bankKebAmount);

        BankAmountEntity bankNonghyupAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.NONGHYUP_BANK.getBankName())
                .amount(nonghyupBankAmount).build();
        bankAmountEntities.add(bankNonghyupAmount);

        BankAmountEntity bankShinhanAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.SHINHAN_BANK.getBankName())
                .amount(shinhanBankAmount).build();
        bankAmountEntities.add(bankShinhanAmount);

        BankAmountEntity bankEtcAmount = BankAmountEntity.builder()
                .year(year)
                .month(month)
                .bankName(BankEnum.ETC_BANK.getBankName())
                .amount(etcBankAmount).build();
        bankAmountEntities.add(bankEtcAmount);
        bankAmountRepository.saveAll(bankAmountEntities);*/

        return uploadFileRecord;
    }

    private boolean isFileExtensionCsv(MultipartFile file){
        if(FileExtensionEnum.FILE_EXTENSION.hasFileExtensions(file.getContentType())){
            return false;
        }
        return true;
    }

    public void parseBankList(String content){

        // 불필요한 데이터 가공
        Optional<String> columnNames = Arrays.stream(content.split("\r\n")).findFirst();
        String removeWord = columnNames.get().replaceAll("억원","");
        String removeSpecialAndNum = removeWord.replaceAll("[()0-9]","");

        String[] columns = removeSpecialAndNum.split(",");

        // BankEntity 리스트 생성
        List<BankEntity> bankEntities = new ArrayList<>();
        for(String each : columns){
            if(each.equals("연도") || each.equals("월") || each.equals("주택도시기금") || each.isEmpty()){
                continue;
            }else{
                BankEntity bankEntity = new BankEntity();
                BankEnum bankEnum = BankEnum.fromString(each);
                bankEntity.setBankCode(bankEnum.getBankCode());
                bankEntity.setBankName(bankEnum.getBankName());
                bankEntity.setBankEnum(bankEnum);
                bankEntities.add(bankEntity);
            }
        }

        bankRepository.saveAll(bankEntities);
    }

    public List<SupplyAmount> findSupplyAmountByYear(String eachYear){
        return supplyAmountRespository.findSupplyAmountEntitiesByYear(eachYear)
                .stream()
                .map(SupplyAmount::fromSupplyAmount)
                .collect(Collectors.toList());
    }

    public List<SupplyAmountSumEntity> sumSupplyAmountByYear(List<String> year){

        List<SupplyAmountSumEntity> supplyAmountSumEntities = new ArrayList<>();

        for(String eachYear : year){
            //각 년도별 지원금액을 찾는다.
            List<SupplyAmount> supplyAmounts = findSupplyAmountByYear(eachYear);

            //각 년도별 지원금액의 합계를 저장한다.
            SupplyAmountSumEntity supplyAmount = SupplyAmountSumEntity.builder()
                    .year(eachYear)
                    .wooriBank(supplyAmounts.stream().mapToInt(SupplyAmount::getWooriBankAmount).sum())
                    .shinhanBank(supplyAmounts.stream().mapToInt(SupplyAmount::getShinhanBankAmount).sum())
                    .nonghyupBank(supplyAmounts.stream().mapToInt(SupplyAmount::getNonghyupBankAmount).sum())
                    .kebBank(supplyAmounts.stream().mapToInt(SupplyAmount::getKebBankAmount).sum())
                    .kbBank(supplyAmounts.stream().mapToInt(SupplyAmount::getKbBankAmount).sum())
                    .housingCityFund(supplyAmounts.stream().mapToInt(SupplyAmount::getHousingCityFundAmount).sum())
                    .hanaBank(supplyAmounts.stream().mapToInt(SupplyAmount::getHanaBankAmount).sum())
                    .etcBank(supplyAmounts.stream().mapToInt(SupplyAmount::getEtcBankAmount).sum())
                    .cityBank(supplyAmounts.stream().mapToInt(SupplyAmount::getCityBankAmount).sum())
                    .build();

            supplyAmountSumEntities.add(supplyAmount);
        }
        log.info("SupplyAmounts sum with year = {}", supplyAmountSumEntities);
        return supplyAmountSumEntities;
    }
    @Transactional
    public FileUploadResult saveFileRecords(MultipartFile file){

        if(isFileExtensionCsv(file)){ throw new AppException(ErrorCodeType.FILE_EXTENSION_NOT_FOUND); }
        if(file.isEmpty()){ throw new AppException(ErrorCodeType.FILE_EMPTY); }

        List<SupplyAmountEntity> supplyAmountEntityList = null;

        try {
            String contents = new String(file.getBytes(), Charset.forName("EUC-KR"));
            parseBankList(contents);
            supplyAmountEntityList = Arrays.stream(contents.split("\n"))
                    .skip(1)
                    .map(this::parsingFromUploadFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO save 과 saveAndFlush 차이점 및 왜 사용했는지 https://ramees.tistory.com/36

        fileUploadRecordRepository.saveAll(supplyAmountEntityList);

        //각 년도별 지원금액의 합계를 저장해둔다.
        List<SupplyAmountSumEntity> supplyAmountSumEntities = sumSupplyAmountByYear(getDistinctYear());
        supplyAmountSumRespository.saveAll(supplyAmountSumEntities);
        log.info("saveFileRecords - supplyAmountSumEntities = {}", supplyAmountSumEntities);

        FileUploadResult fileUploadResult = FileUploadResult.builder()
                .fileType(file.getContentType())
                .fileRecordCount(supplyAmountEntityList.size())
                .fileByteSize(file.getSize()).build();

        log.info("Upload Multipart File Information = {}", fileUploadResult);

        return fileUploadResult;
    }

    public List<String> getDistinctYear(){
        return supplyAmountRespository.findDistinctYear();
    }
}
