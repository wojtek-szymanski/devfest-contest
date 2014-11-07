package pl.allegro.devfest;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BestDealAdvisor {

    private final Collection<Auction> auctions;

    public BestDealAdvisor(Collection<Auction> auctions) {
        this.auctions = Collections.unmodifiableCollection(auctions);
    }

    public Optional<BestDeal> findBestDeal(DealCriteria dealRequirements) {
        throw new UnsupportedOperationException("Implement me!");
    }
}
