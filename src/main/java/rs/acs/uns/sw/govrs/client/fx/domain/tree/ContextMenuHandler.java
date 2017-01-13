package rs.acs.uns.sw.govrs.client.fx.domain.tree;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.*;
import rs.acs.uns.sw.govrs.client.fx.util.CustomDialogCreator;

import java.util.Optional;

public class ContextMenuHandler {

    /**
     * Creates context menu for provided Element
     *
     * @param element Element
     * @return ContextMenu
     */
    public ContextMenu createContextMenu(Element element) {
        if (element instanceof Law) {
            return this.createLawContextMenu((Law) element);
        }
        if (element instanceof Chapter) {
            return this.createChapterContextMenu((Chapter) element);
        }
        if (element instanceof Part) {
            return this.createPartContextMenu((Part) element);
        }
        if (element instanceof Section) {
            return this.createSectionContextMenu((Section) element);
        }
        if (element instanceof Subsection) {
            return this.createSubsectionContextMenu((Subsection) element);
        }
        if (element instanceof Article) {
            return this.createArticleContextMenu((Article) element);
        }
        if (element instanceof Paragraph) {
            return this.createParagraphContextMenu((Paragraph) element);
        }
        if (element instanceof Clause) {
            return this.createClauseContextMenu((Clause) element);
        }
        if (element instanceof Subclause) {
            return this.createSubclauseContextMenu((Subclause) element);
        }
        if (element instanceof Item) {
            return this.createItemContextMenu((Item) element);
        }
        if (element instanceof StringElement) {
            return this.createTextContextMenu((StringElement) element);
        }
        return null;
    }

    private ContextMenu createLawContextMenu(Law law) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem chapterMenuItem = new MenuItem("Novi deo", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/chapter.png"))));
        MenuItem partMenuItem = new MenuItem("Nova glava", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/part.png"))));
        contextMenu.getItems().add(partMenuItem);
        contextMenu.getItems().add(chapterMenuItem);
        createInsertAction(chapterMenuItem, "Deo", law, new Chapter());
        createInsertAction(partMenuItem, "Glava", law, new Part());
        return contextMenu;
    }

    private ContextMenu createChapterContextMenu(Chapter chapter) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem partMenuItem = new MenuItem("Nova glava", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/part.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(partMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(partMenuItem, "Glava", chapter, new Part());
        createDeleteAction(deleteMenuItem, chapter);
        return contextMenu;
    }

    private ContextMenu createPartContextMenu(Part part) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem sectionMenuItem = new MenuItem("Novi odeljak", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/section.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(sectionMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(sectionMenuItem, "Odeljak", part, new Section());
        createDeleteAction(deleteMenuItem, part);
        return contextMenu;
    }

    private ContextMenu createSectionContextMenu(Section section) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem subsectionMenuItem = new MenuItem("Novi pododeljak", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/subsection.png"))));
        MenuItem articleMenuItem = new MenuItem("Novi član", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/article.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(subsectionMenuItem);
        contextMenu.getItems().add(articleMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(subsectionMenuItem, "Pododeljak", section, new Subsection());
        createInsertAction(articleMenuItem, "Član", section, new Article());
        createDeleteAction(deleteMenuItem, section);
        return contextMenu;
    }

    private ContextMenu createSubsectionContextMenu(Subsection subsection) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem articleMenuItem = new MenuItem("Novi član", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/article.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(articleMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(articleMenuItem, "Član", subsection, new Article());
        createDeleteAction(deleteMenuItem, subsection);
        return contextMenu;
    }

    private ContextMenu createArticleContextMenu(Article article) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem paragraphMenuItem = new MenuItem("Novi stav", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/paragraph.png"))));
        MenuItem textMenuItem = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(paragraphMenuItem);
        contextMenu.getItems().add(textMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(paragraphMenuItem, "Stav", article, new Paragraph());
        createInsertAction(textMenuItem, "Tekst", article, new StringElement(""));
        createDeleteAction(deleteMenuItem, article);
        return contextMenu;
    }

    private ContextMenu createParagraphContextMenu(Paragraph paragraph) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem clauseMenuItem = new MenuItem("Nova tačka", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/clause.png"))));
        MenuItem textMenuItem = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(clauseMenuItem);
        contextMenu.getItems().add(textMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(clauseMenuItem, "Tačka", paragraph, new Clause());
        createInsertAction(textMenuItem, "Tekst", paragraph, new StringElement(""));
        createDeleteAction(deleteMenuItem, paragraph);
        return contextMenu;
    }

    private ContextMenu createClauseContextMenu(Clause clause) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem subclauseMenuItem = new MenuItem("Nova podtačka", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/subclause.png"))));
        MenuItem textMenuItem = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(subclauseMenuItem);
        contextMenu.getItems().add(textMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(subclauseMenuItem, "Podtačka", clause, new Subclause());
        createInsertAction(textMenuItem, "Tekst", clause, new StringElement(""));
        createDeleteAction(deleteMenuItem, clause);
        return contextMenu;
    }

    private ContextMenu createSubclauseContextMenu(Subclause subclause) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemMenuItem = new MenuItem("Nova alineja", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/item.png"))));
        MenuItem textMenuItem = new MenuItem("Novi tekst", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/text.png"))));
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(itemMenuItem);
        contextMenu.getItems().add(textMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(deleteMenuItem);
        createInsertAction(itemMenuItem, "Alineja", subclause, new Item());
        createInsertAction(textMenuItem, "Tekst", subclause, new StringElement(""));
        createDeleteAction(deleteMenuItem, subclause);
        return contextMenu;
    }

    private ContextMenu createItemContextMenu(Item item) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(deleteMenuItem);
        createDeleteAction(deleteMenuItem, item);
        return contextMenu;
    }

    private ContextMenu createTextContextMenu(StringElement text) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = createDeleteMenuItem();
        contextMenu.getItems().add(deleteMenuItem);
        deleteMenuItem.setDisable(false);
        createDeleteAction(deleteMenuItem, text);
        return contextMenu;
    }

    private MenuItem createDeleteMenuItem() {
        return new MenuItem("Ukloni", new ImageView(new Image(MainFXApp.class.getResourceAsStream("/images/tree_images/delete.png"))));
    }

    private void createDeleteAction(MenuItem deleteItem, Element element) {
        deleteItem.setOnAction(event -> {
            Alert deleteAlert = CustomDialogCreator.createDeleteConfirmationDialog(element.getElementName());
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if (result.get() == CustomDialogCreator.YES) {
                element.getElementParent().removeChild(element);
            }
        });
    }

    private void createInsertAction(MenuItem menuItem, String promptName, Element parent, Element newElement) {
        menuItem.setOnAction(event -> {
            TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog(promptName);
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                newElement.setElementName(name);
                parent.createAndAddChild(newElement);
            });
        });
    }
}
