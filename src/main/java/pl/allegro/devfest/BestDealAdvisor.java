package pl.allegro.devfest;

import java.util.*;
import java.util.stream.Collectors;

public class BestDealAdvisor {

    private final Collection<Auction> auctions;

    public BestDealAdvisor(Collection<Auction> auctions) {
        this.auctions = Collections.unmodifiableCollection(auctions);
    }

    public Optional<BestDeal> findBestDeal(DealCriteria dealRequirements) {
        Collection<Auction> cheapestAuctions = findCheapestAuctions(dealRequirements);
        return BestDeal.createBestDeal(dealRequirements, cheapestAuctions);
    }

    private Collection<Auction> findCheapestAuctions(DealCriteria dealRequirements) {
        return auctions.stream().
                filter(auction -> dealRequirements.getGoodTypes().contains(auction.getGoodType())).
                filter(auction -> dealRequirements.getBudget().compareTo(auction.getPrice()) >= 0).
                collect(Collectors.groupingBy(Auction::getGoodType)).
                values().stream().map(auctions -> auctions.stream().
                min(Comparator.comparing(Auction::getPrice)).get()).
                collect(Collectors.toList());
    }

}
