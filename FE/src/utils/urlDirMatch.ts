export function urlDirMatch(url: string, target: string, maxDepth: number): boolean {
  // Remove query string
  const path = url.split("?")[0];

  // Split into segments and remove empty ones
  const segments = path.split("/").filter(Boolean);

  const index = segments.indexOf(target);
  if (index === -1) return false;
  const depthAfter = segments.length - index - 1;

  return depthAfter <= maxDepth;
}
