import java.time.LocalDateTime;

public class Todo {
    private String text;
    private LocalDateTime due;
    private Category cat;
    private Importance importance;
    private Status completion;

    public Todo(String text, LocalDateTime due, Category cat,
                Importance importance, Status completion) {
        this.text = text;
        this.due = due;
        this.cat = cat;
        this.importance = importance;
        this.completion = completion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDue() {
        return due;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Status getCompletion() {
        return completion;
    }

    public void setCompletion(Status completion) {
        this.completion = completion;
    }


    @Override
    public String toString() {
        return cat.getColour() + "Todo{" +
                "text = " + text +
                ", due = " + due +
                ", cat = " + cat +
                ", importance = " + importance +
                "," + "\n" + "completion = " + completion +
                "}\033[0m";
    }
}
