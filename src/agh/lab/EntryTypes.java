package agh.lab;

import java.util.*;

public class EntryTypes {
    public Map<String, List<String>> required = new HashMap<>();
    public Map<String, List<String>> possibleTags = new HashMap<>();

    public EntryTypes(){
        setType(
                "article",
                new String[]{"author", "title", "journal", "year"},
                new String[]{"number", "pages", "month", "note", "key", "volume"});
        setType(
                "book",
                new String[]{"title", "publisher", "year"},
                new String[]{"author", "editor", "volume", "number", "series", "address", "edition", "month", "note", "key", "booktitle"});
        setType(
                "booklet",
                new String[]{"title"},
                new String[]{"author", "howpublished", "address", "month", "year", "note", "key"});
        setType(
                "conference",
                new String[]{"author", "title", "booktitle", "year"},
                new String[]{"editor", "volume", "number", "series", "pages",
                        "address", "month", "organisation", "publisher", "note", "key"});
        setType(
                "inbook",
                new String[]{"title","publisher", "year"},
                new String[]{"chapter", "pages", "author", "editor", "volume", "number", "series", "type", "address",
                        "edition", "month", "note", "key"});
        setType(
                "incollection",
                new String[]{"author", "title", "booktitle", "publisher", "year"},
                new String[]{"editor", "volume", "number", "series", "type", "chapter", "pages", "address", "edition", "month", "note", "key"});
        setType(
                "inproceedings",
                new String[]{"author", "title", "booktitle", "year"},
                new String[]{"editor", "volume", "number", "series", "pages",
                        "address", "month", "organization", "organisation", "publisher", "note", "key"});
        setType(
                "manual",
                new String[]{"title"},
                new String[]{"author", "organisation","organization", "address", "edition", "month", "year", "note", "key"});
        setType(
                "mastersthesis",
                new String[]{"author", "title", "school", "year"},
                new String[]{"type", "address", "month", "note", "key"});
        setType(
                "phdthesis",
                new String[]{"author", "title", "school", "year"},
                new String[]{"type", "address", "month", "note", "key"});
        setType(
                "proceedings",
                new String[]{"title", "year"},
                new String[]{"editor", "volume", "number", "series", "address", "month", "publisher", "organization", "organisation", "note", "key", "booktitle"});
        setType(
                "techreport",
                new String[]{"author", "title", "institution", "year"},
                new String[]{"type", "number", "address", "month", "note", "key"});
        setType(
                "misc",
                new String[]{},
                new String[]{"author", "title", "howpublished", "month", "year", "note", "key"});
        setType(
                "unpublished",
                new String[]{"author", "title", "note"},
                new String[]{"month", "year", "key"});
    }

    public void checkEntries(Map<String, Entry> entries) throws ParserException {
        for (Entry entry : entries.values()) {
            String type = entry.getType();
            Map<String, String> tags = entry.getTags();

            // Look for crossReference
            Map<String, String> crossTags = new HashMap<>();
            if (tags.containsKey("crossref")) {
                String crossId = tags.get("crossref").toLowerCase();
                if (entries.containsKey(crossId)) {
                    crossTags = entries.get(crossId).getTags();
                } else
                    throw new ParserException("No cross-reference \"" + crossId + "\" exists.");
            }
            for (Map.Entry<String, String> e : crossTags.entrySet()) {
                if(!tags.containsKey(e.getKey()))
                    tags.put(e.getKey(), e.getValue());
            }
            tags.remove("crossref");

            if (required.containsKey(type)) {
                for (String req : required.get(type)) {
                    if (!tags.containsKey(req) && !crossTags.containsKey(req)) {
                        throw new ParserException("Entry: \n" + entry
                                + "  does not contain required \"" + req + "\" field.");
                    }
                }
            } else {
                throw new ParserException("No such type as \"" + type + "\"");
            }
            if (possibleTags.containsKey(type)) {
                for (String opt : tags.keySet()) {
                    if (!possibleTags.get(type).contains(opt))
                        throw new ParserException("Entry: \n" + entry
                                + "  contains unknown \"" + opt + "\" field.");
                }
            }
        }

    }

    private void setType(String type, String[] req, String[] opt) {
        List<String> rList = new LinkedList<>(Arrays.asList(req));
        List<String> oList = new LinkedList<>(Arrays.asList(req));
        oList.addAll(Arrays.asList(opt));
        required.put(type, rList);
        possibleTags.put(type, oList);
    }
}
