package pl.allegro.devfest;

import java.util.*;
import java.util.stream.Collectors;

public class BestDealAdvisor {

    private final Collection<Auction> auctions;

    public BestDealAdvisor(Collection<Auction> auctions) {
        this.auctions = Collections.unmodifiableCollection(auctions);
    }

    public Optional<BestDeal> findBestDeal(DealCriteria dealRequirements) {
        Collection<Auction> cheapestAuctions = findCheapestAuctions(dealRequirements.getGoodTypes());
        return BestDeal.createBestDeal(dealRequirements, cheapestAuctions);
    }

    private Collection<Auction> findCheapestAuctions(Set<GoodType> goodTypes) {
        return auctions.stream().
                filter(t -> goodTypes.contains(t.getGoodType())).
                collect(Collectors.groupingBy(t -> t.getGoodType())).
                values().stream().map(a -> a.stream().
                min(Comparator.comparing(p -> p.getPrice())).get()).
                collect(Collectors.toList());
    }

}
