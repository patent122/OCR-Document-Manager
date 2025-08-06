create table if not exists documents (
  id              integer primary key autoincrement,
  file_name       text not null,
  file_path       text not null,
  mime_type       text,
  size_bytes      integer not null,
  created_at      text not null default (datetime('now')),
  sha256          text,
  ocr_done        integer not null default 0
);
create index if not exists idx_documents_sha256 on documents(sha256);
