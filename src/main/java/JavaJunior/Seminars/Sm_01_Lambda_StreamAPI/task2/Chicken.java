package JavaJunior.Seminars.Sm_01_Lambda_StreamAPI.task2;

/**
 * Курица
 */
public class Chicken implements HealthyFood{
    @Override
    public boolean getProteins() {
        return true;
    }

    @Override
    public boolean getFats() {
        return false;
    }

    @Override
    public boolean getCarbohydrates() {
        return false;
    }

    @Override
    public String getName() {
        return "Курица";
    }
}

