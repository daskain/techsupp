package ru.rfibank.techsupport.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import ru.rfibank.techsupport.pojo.BinInfo;
import ru.rfibank.techsupport.service.GetBinByBinInFileService;

//вместо файл с бинами вместо базы
@Service
public class GetBinByBinInFileImpl implements GetBinByBinInFileService{

	@Override
	public Map<String, BinInfo> getBinInFile(String bin) throws IOException {
		
		Map<String, BinInfo> result = new HashMap<>();
		Files.lines(Paths.get("bin_table.txt"))
        .filter(s -> s.contains(bin))
        .forEach((entry) -> {
        	String[] buf = entry.split("\\t");
        	BinInfo data = new BinInfo();
        	
        	data.setPaymentSystem(buf[0]);
        	data.setBin(buf[1]);
        	data.setCountryCode(buf[2]);
        	data.setCountryNum(buf[3]);
        	result.put(data.getBin(), data);
        });
		return result;
	}

}
