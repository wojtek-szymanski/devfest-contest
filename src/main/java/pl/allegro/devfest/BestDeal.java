package pl.allegro.devfest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class BestDeal {

    private final Collection<Auction> auctions;

    public BestDeal(Collection<Auction> auctions) {
        this.auctions = Collections.unmodifiableCollection(auctions);
    }

    public Collection<Auction> getAuctions() {
        return Collections.unmodifiableCollection(auctions);
    }

    public BigDecimal getTotalPrice() {
        return auctions
                .stream()
                .map(auction -> auction.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Optional<BestDeal> createBestDeal(DealCriteria dealRequirements, Collection<Auction> auctions) {
        BestDeal bestDeal = new BestDeal(auctions);
        Set<GoodType> goodTypes = dealRequirements.getGoodTypes();
        BigDecimal budget = dealRequirements.getBudget();
        BigDecimal totalPrice = bestDeal.getTotalPrice();
        return goodTypes.size() != auctions.size() || budget.compareTo(totalPrice) < 0 ? Optional.empty() : Optional.of(bestDeal);
    }
}
