package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.CustomDialogCreator;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;

import java.util.Optional;

public class ContextMenuHandler {

    /**
     * Creates context menu for provided Element
     * @param element   Element
     * @return ContextMenu
     */
    public ContextMenu createContextMenu(Element element) {
        if (element instanceof Law) {
            return this.createLawContextMenu((Law)element);
        }
        if (element instanceof Chapter) {
            return this.createChapterContextMenu((Chapter)element);
        }
        if (element instanceof Part) {
            return this.createPartContextMenu((Part)element);
        }
        if (element instanceof Section) {
            return this.createSectionContextMenu((Section)element);
        }
        if (element instanceof Subsection) {
            return this.createSubsectionContextMenu((Subsection)element);
        }
        if (element instanceof Article) {
            return this.createArticleContextMenu((Article)element);
        }
        if (element instanceof Paragraph) {
            return this.createParagraphContextMenu((Paragraph)element);
        }
        if (element instanceof Clause) {
            return this.createClauseContextMenu((Clause)element);
        }
        if (element instanceof Subclause) {
            return this.createSubclauseContextMenu((Subclause)element);
        }
        if (element instanceof Item) {
            return this.createItemContextMenu((Item)element);
        }
        if (element instanceof StringElement) {
            return this.createTextContextMenu((StringElement)element);
        }
        return null;
    }

    private ContextMenu createLawContextMenu(Law law) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newChapter = new MenuItem("Novi deo", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/chapter.png"))));
        MenuItem newPart = new MenuItem("Nova glava", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/part.png"))));
        contextMenu.getItems().add(newPart);
        contextMenu.getItems().add(newChapter);
        newPart.setOnAction(event -> {
            TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Glava");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                Part p = new Part();
                p.setName(name);
                law.createAndAddChild(p);
            });
        });
        newChapter.setOnAction(event -> {
            TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Deo");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                Chapter c = new Chapter();
                c.setName(name);
                law.createAndAddChild(c);
            });
        });
        return contextMenu;
    }

    private ContextMenu createPartContextMenu(Part part) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newSection = new MenuItem("Novi odeljak", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/section.png"))));
        contextMenu.getItems().add(newSection);
        newSection.setOnAction(event -> part.createAndAddChild(new Section()));
        return contextMenu;
    }

    private ContextMenu createChapterContextMenu(Chapter chapter) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newSection = new MenuItem("Nova glava", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/part.png"))));
        contextMenu.getItems().add(newSection);
        newSection.setOnAction(event -> chapter.createAndAddChild(new Part()));
        return contextMenu;
    }

    private ContextMenu createSectionContextMenu(Section section) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newSubsection = new MenuItem("Novi pododeljak", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/subsection.png"))));
        MenuItem newArticle = new MenuItem("Novi član", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/article.png"))));
        contextMenu.getItems().add(newSubsection);
        contextMenu.getItems().add(newArticle);
        newSubsection.setOnAction(event -> section.createAndAddChild(new Subsection()));
        newArticle.setOnAction(event -> section.createAndAddChild(new Article()));
        return contextMenu;
    }

    private ContextMenu createSubsectionContextMenu(Subsection subsection) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newArticle = new MenuItem("Novi član", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/article.png"))));
        contextMenu.getItems().add(newArticle);
        newArticle.setOnAction(event -> subsection.createAndAddChild(new Article()));
        return contextMenu;
    }

    private ContextMenu createArticleContextMenu(Article article) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newParagraph = new MenuItem("Novi stav", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/paragraph.png"))));
        MenuItem newText = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        contextMenu.getItems().add(newParagraph);
        contextMenu.getItems().add(newText);
        newParagraph.setOnAction(event -> article.createAndAddChild(new Paragraph()));
        newText.setOnAction(event -> article.createAndAddChild(new StringElement("")));
        return contextMenu;
    }

    private ContextMenu createParagraphContextMenu(Paragraph paragraph) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newClause = new MenuItem("Nova tačka", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/clause.png"))));
        MenuItem newText = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        contextMenu.getItems().add(newClause);
        contextMenu.getItems().add(newText);
        newClause.setOnAction(event -> paragraph.createAndAddChild(new Clause()));
        newText.setOnAction(event -> paragraph.createAndAddChild(new StringElement("")));
        return contextMenu;
    }

    private ContextMenu createClauseContextMenu(Clause clause) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newSubclause = new MenuItem("Nova podtačka", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/subclause.png"))));
        MenuItem newText = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        contextMenu.getItems().add(newSubclause);
        contextMenu.getItems().add(newText);
        newSubclause.setOnAction(event -> clause.createAndAddChild(new Subclause()));
        newText.setOnAction(event -> clause.createAndAddChild(new StringElement("")));
        return contextMenu;
    }

    private ContextMenu createSubclauseContextMenu(Subclause subclause) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newItem = new MenuItem("Nova alineja", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/item.png"))));
        MenuItem newText = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        contextMenu.getItems().add(newItem);
        contextMenu.getItems().add(newText);
        newItem.setOnAction(event -> subclause.createAndAddChild(new Item()));
        newText.setOnAction(event -> subclause.createAndAddChild(new StringElement("")));
        return contextMenu;
    }

    private ContextMenu createItemContextMenu(Item item) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Ukloni", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/delete.png"))));
        deleteItem.setDisable(true);
        contextMenu.getItems().add(deleteItem);
        return contextMenu;
    }

    private ContextMenu createTextContextMenu(StringElement text) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Ukloni", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/delete.png"))));
        deleteItem.setDisable(true);
        contextMenu.getItems().add(deleteItem);
        return contextMenu;
    }
}
