import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MealPlannerApp {

    public static class Food {
        private String name;
        private int calories;
        private boolean isVegetarian;

        public Food(String name, int calories, boolean isVegetarian) {
            this.name = name;
            this.calories = calories;
            this.isVegetarian = isVegetarian;
        }

        public String getName() { return name; }
        public int getCalories() { return calories; }
        public boolean isVegetarian() { return isVegetarian; }

        @Override
        public String toString() {
            return name + " (" + calories + " cal)";
        }
    }

    public static class MealPlanner {
        private List<Food> foodList;
        private Map<String, List<String>> weightLossSuggestions;
        private Map<String, List<String>> weightGainSuggestions;

        public MealPlanner(List<Food> foodList,
                           Map<String, List<String>> weightLossSuggestions,
                           Map<String, List<String>> weightGainSuggestions) {
            this.foodList = foodList;
            this.weightLossSuggestions = weightLossSuggestions;
            this.weightGainSuggestions = weightGainSuggestions;
        }

        public List<Food> generateMealPlan(int calorieLimit, boolean isVegetarian, String fitnessGoal) {
            List<Food> filteredFoods = filterFoods(isVegetarian);
            List<Food> mealPlan = new ArrayList<>();
            int currentCalories = 0;

            // Add high-calorie foods based on the fitness goal and dietary preference
            if (!isVegetarian && "weight gain".equalsIgnoreCase(fitnessGoal)) {
                addHighCalorieFoods(mealPlan, List.of("Chicken", "Egg", "Mutton"), calorieLimit);
            } else if (isVegetarian && "weight gain".equalsIgnoreCase(fitnessGoal)) {
                addHighCalorieFoods(mealPlan, List.of("Paneer"), calorieLimit);
            }

            // Fill the remaining calorie limit with other foods
            for (Food food : filteredFoods) {
                if (currentCalories + food.getCalories() <= calorieLimit && !mealPlan.contains(food)) {
                    mealPlan.add(food);
                    currentCalories += food.getCalories();
                }
            }

            return mealPlan;
        }

        private void addHighCalorieFoods(List<Food> mealPlan, List<String> foodNames, int calorieLimit) {
            int currentCalories = mealPlan.stream().mapToInt(Food::getCalories).sum();
            for (String foodName : foodNames) {
                Food food = foodList.stream()
                        .filter(f -> f.getName().equalsIgnoreCase(foodName))
                        .findFirst()
                        .orElse(null);
                if (food != null && currentCalories + food.getCalories() <= calorieLimit) {
                    mealPlan.add(food);
                    currentCalories += food.getCalories();
                }
            }
        }

        private List<Food> filterFoods(boolean isVegetarian) {
            List<Food> filteredFoods = new ArrayList<>();
            for (Food food : foodList) {
                if (food.isVegetarian() == isVegetarian || !isVegetarian) {
                    filteredFoods.add(food);
                }
            }
            return filteredFoods;
        }

        public List<String> getSuggestions(String foodItem, String fitnessGoal) {
            if ("weight loss".equalsIgnoreCase(fitnessGoal)) {
                return weightLossSuggestions.getOrDefault(foodItem, new ArrayList<>());
            } else if ("weight gain".equalsIgnoreCase(fitnessGoal)) {
                return weightGainSuggestions.getOrDefault(foodItem, new ArrayList<>());
            } else {
                return new ArrayList<>();
            }
        }
    }

    public static void main(String[] args) {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Chapati", 71, true));
        foodList.add(new Food("Rice", 130, true));
        foodList.add(new Food("Paneer", 296, true));
        foodList.add(new Food("Egg", 155, false));
        foodList.add(new Food("Dal", 150, true));
        foodList.add(new Food("Chole (Chickpeas)", 200, true));
        foodList.add(new Food("Curd", 98, true));
        foodList.add(new Food("Vegetable", 80, true));
        foodList.add(new Food("Milk", 42, true));
        foodList.add(new Food("Butter", 717, true));
        foodList.add(new Food("Ghee", 900, true));
        foodList.add(new Food("Yogurt", 59, true));
        foodList.add(new Food("Chicken", 239, false));
        foodList.add(new Food("Mutton", 294, false));
        foodList.add(new Food("Fish", 206, false));
        foodList.add(new Food("Lentils", 116, true));
        foodList.add(new Food("Spinach", 23, true));
        foodList.add(new Food("Sweet Potato", 86, true));
        foodList.add(new Food("Rava (Semolina)", 360, true));
        foodList.add(new Food("Whole Wheat Flour", 340, true));
        foodList.add(new Food("Oats", 389, true));
        foodList.add(new Food("Rajma (Kidney Beans)", 127, true));
        foodList.add(new Food("Cucumber", 16, true));
        foodList.add(new Food("Tomato", 18, true));
        foodList.add(new Food("Onion", 40, true));
        foodList.add(new Food("Bell Pepper", 20, true));
        foodList.add(new Food("Green Beans", 31, true));
        foodList.add(new Food("Carrot", 41, true));

        Map<String, List<String>> weightLossSuggestions = new HashMap<>();
        weightLossSuggestions.put("Chapati", List.of("Chapati with Dal", "Chapati with Vegetable"));
        weightLossSuggestions.put("Rice", List.of("Rice with Dal Tadka", "Rice with Vegetable Stir-fry"));
        weightLossSuggestions.put("Dal", List.of("Dal Tadka", "Dal Soup"));
        weightLossSuggestions.put("Chole (Chickpeas)", List.of("Chole Salad", "Chole Soup"));
        weightLossSuggestions.put("Curd", List.of("Curd with Fruits", "Curd Salad"));
        weightLossSuggestions.put("Vegetable", List.of("Mixed Vegetable Curry", "Vegetable Soup"));
        weightLossSuggestions.put("Spinach", List.of("Spinach Soup", "Palak Salad"));
        weightLossSuggestions.put("Sweet Potato", List.of("Sweet Potato Salad", "Sweet Potato Bake"));
        weightLossSuggestions.put("Cucumber", List.of("Cucumber Salad", "Cucumber Raita"));
        weightLossSuggestions.put("Carrot", List.of("Carrot Salad", "Carrot Soup"));

        Map<String, List<String>> weightGainSuggestions = new HashMap<>();
        weightGainSuggestions.put("Paneer", List.of("Paneer Butter Masala", "Paneer Tikka"));
        weightGainSuggestions.put("Egg", List.of("Egg Curry", "Egg Bhurji"));
        weightGainSuggestions.put("Butter", List.of("Butter Chicken", "Butter Paneer"));
        weightGainSuggestions.put("Ghee", List.of("Ghee Rice", "Ghee Roast"));
        weightGainSuggestions.put("Yogurt", List.of("Yogurt with Granola", "Yogurt Parfait"));
        weightGainSuggestions.put("Chicken", List.of("Chicken Curry", "Chicken Kebab"));
        weightGainSuggestions.put("Mutton", List.of("Mutton Curry", "Mutton Kebab"));
        weightGainSuggestions.put("Fish", List.of("Fish Curry", "Grilled Fish"));

        MealPlanner planner = new MealPlanner(foodList, weightLossSuggestions, weightGainSuggestions);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your daily calorie limit: ");
        int calorieLimit = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Are you vegetarian (yes/no)? ");
        boolean isVegetarian = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Enter your fitness goal (weight loss/weight gain): ");
        String fitnessGoal = scanner.nextLine();

        List<Food> mealPlan = planner.generateMealPlan(calorieLimit, isVegetarian, fitnessGoal);

        System.out.println("Your meal plan:");
        for (Food food : mealPlan) {
            System.out.println(food);
            List<String> suggestions = planner.getSuggestions(food.getName(), fitnessGoal);
            if (!suggestions.isEmpty()) {
                System.out.println("  Suggestions: " + String.join(", ", suggestions));
            }
        }

        scanner.close();
    }
}