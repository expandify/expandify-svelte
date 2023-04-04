package de.wittenbude.expandify.repositories.shows;

import de.wittenbude.expandify.models.db.ShowInfo;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShowInfoDao implements IDao<ShowInfo> {

    private final ShowInfoRepository showInfoRepository;

    public ShowInfoDao(ShowInfoRepository showInfoRepository) {
        this.showInfoRepository = showInfoRepository;
    }

    @Override
    public Optional<ShowInfo> find(String id) {
        return showInfoRepository.findById(id);
    }

    @Override
    public Optional<ShowInfo> find(ShowInfo showInfo) {
        return showInfoRepository.findById(showInfo.getId());
    }

    @Override
    public ShowInfo save(ShowInfo showInfo) {
        return showInfoRepository.save(showInfo);
    }

    @Override
    public List<ShowInfo> saveAll(List<ShowInfo> showInfos) {
        return showInfoRepository.saveAll(showInfos);
    }
}
